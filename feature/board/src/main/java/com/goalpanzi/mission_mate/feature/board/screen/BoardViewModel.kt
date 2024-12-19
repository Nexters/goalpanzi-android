package com.goalpanzi.mission_mate.feature.board.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goalpanzi.mission_mate.core.domain.common.DomainResult
import com.goalpanzi.mission_mate.core.domain.common.model.mission.MissionStatus.Companion.statusString
import com.goalpanzi.mission_mate.core.domain.mission.model.BoardReward
import com.goalpanzi.mission_mate.core.domain.mission.usecase.DeleteMissionUseCase
import com.goalpanzi.mission_mate.core.domain.mission.usecase.GetMissionBoardsUseCase
import com.goalpanzi.mission_mate.core.domain.mission.usecase.GetMissionUseCase
import com.goalpanzi.mission_mate.core.domain.mission.usecase.GetMissionVerificationsUseCase
import com.goalpanzi.mission_mate.core.domain.mission.usecase.GetMyMissionVerificationUseCase
import com.goalpanzi.mission_mate.core.domain.mission.usecase.ViewVerificationUseCase
import com.goalpanzi.mission_mate.core.domain.onboarding.usecase.GetJoinedMissionsUseCase
import com.goalpanzi.mission_mate.core.domain.setting.usecase.GetViewedTooltipUseCase
import com.goalpanzi.mission_mate.core.domain.setting.usecase.SetViewedTooltipUseCase
import com.goalpanzi.mission_mate.core.domain.user.usecase.GetCachedMemberIdUseCase
import com.goalpanzi.mission_mate.core.domain.user.usecase.ProfileUseCase
import com.goalpanzi.mission_mate.feature.board.model.BoardPiece
import com.goalpanzi.mission_mate.feature.board.model.MissionError
import com.goalpanzi.mission_mate.feature.board.model.MissionState
import com.goalpanzi.mission_mate.feature.board.model.MissionState.Companion.getMissionState
import com.goalpanzi.mission_mate.feature.board.model.UserStory
import com.goalpanzi.mission_mate.feature.board.model.toBoardPieces
import com.goalpanzi.mission_mate.feature.board.model.toCharacterUiModel
import com.goalpanzi.mission_mate.feature.board.model.toUiModel
import com.goalpanzi.mission_mate.feature.board.model.uimodel.MissionBoardUiModel
import com.goalpanzi.mission_mate.feature.board.model.uimodel.MissionUiModel
import com.goalpanzi.mission_mate.feature.board.model.uimodel.MissionVerificationUiModel
import com.goalpanzi.mission_mate.feature.board.util.PieceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getViewedTooltipUseCase: GetViewedTooltipUseCase,
    private val getCachedMemberIdUseCase: GetCachedMemberIdUseCase,
    private val getMissionUseCase: GetMissionUseCase,
    private val getMissionBoardsUseCase: GetMissionBoardsUseCase,
    private val getMissionVerificationsUseCase: GetMissionVerificationsUseCase,
    private val deleteMissionUseCase: DeleteMissionUseCase,
    private val profileUseCase: ProfileUseCase,
    private val setViewedTooltipUseCase: SetViewedTooltipUseCase,
    private val getJoinedMissionsUseCase: GetJoinedMissionsUseCase,
    private val getMyMissionVerificationUseCase: GetMyMissionVerificationUseCase,
    private val viewVerificationUseCase: ViewVerificationUseCase
) : ViewModel() {

    val missionId: Long = savedStateHandle.get<Long>("missionId")!!

    val viewedToolTip: StateFlow<Boolean> = getViewedTooltipUseCase().stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = true
    )

    private val _missionError = MutableStateFlow<MissionError?>(null)
    val missionError: StateFlow<MissionError?> = _missionError.asStateFlow()

    private val _myMissionVerification = MutableSharedFlow<UserStory>()
    val myMissionVerification: SharedFlow<UserStory> = _myMissionVerification.asSharedFlow()

    private val _deleteMissionResultEvent = MutableSharedFlow<Boolean>()
    val deleteMissionResultEvent: SharedFlow<Boolean> = _deleteMissionResultEvent.asSharedFlow()

    private val _missionBoardUiModel =
        MutableStateFlow<MissionBoardUiModel>(MissionBoardUiModel.Loading)
    val missionBoardUiModel: StateFlow<MissionBoardUiModel> = _missionBoardUiModel.asStateFlow()

    private val _missionUiModel =
        MutableStateFlow<MissionUiModel>(MissionUiModel.Loading)
    val missionUiModel: StateFlow<MissionUiModel> = _missionUiModel.asStateFlow()

    private val _missionVerificationUiModel =
        MutableStateFlow<MissionVerificationUiModel>(MissionVerificationUiModel.Loading)
    val missionVerificationUiModel: StateFlow<MissionVerificationUiModel> =
        _missionVerificationUiModel.asStateFlow()

    private val _isRefreshLoading = MutableStateFlow(false)
    val isRefreshLoading: StateFlow<Boolean> = _isRefreshLoading.asStateFlow()

    val isHost: StateFlow<Boolean> =
        combine(
            getCachedMemberIdUseCase(),
            missionUiModel.filter { it is MissionUiModel.Success }
        ) { memberId, mission ->
            if (mission !is MissionUiModel.Success) return@combine false
            memberId == mission.missionDetail.hostMemberId
        }.stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    val missionState: StateFlow<MissionState> =
        combine(
            missionBoardUiModel.filter { it is MissionBoardUiModel.Success },
            missionUiModel.filter { it is MissionUiModel.Success },
            missionVerificationUiModel.filter { it is MissionVerificationUiModel.Success }
        ) { missionBoard, mission, missionVerification ->
            getMissionState(missionBoard, mission, missionVerification)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MissionState.LOADING
        )
    private val _boardRewardEvent = MutableSharedFlow<BoardReward?>()
    val boardRewardEvent: SharedFlow<BoardReward?> = _boardRewardEvent.asSharedFlow()

    private val _boardPieces = MutableStateFlow<List<BoardPiece>>(emptyList())
    val boardPieces: StateFlow<List<BoardPiece>> = _boardPieces.asStateFlow()

    fun fetchMissionData() {
        viewModelScope.launch {
            getMissionBoards()
            getMission()
            getMissionVerification()
        }
    }

    fun refreshMissionData() {
        viewModelScope.launch {
            _isRefreshLoading.emit(true)
            joinAll(
                launch { getMissionBoards() },
                launch { getMission() },
                launch { getMissionVerification() }
            )
            _isRefreshLoading.emit(false)
        }
    }

    private suspend fun getMissionBoards() {
        getMissionBoardsUseCase(missionId)
            .catch {
                _missionBoardUiModel.emit(MissionBoardUiModel.Error)
            }.collect {
                when (it) {
                    is DomainResult.Success -> {
                        val myMemberId = getCachedMemberIdUseCase().first()

                        _missionBoardUiModel.emit(
                            MissionBoardUiModel.Success(
                                it.data.toUiModel(
                                    myMemberId
                                )
                            )
                        )

                        val boardPieceList =
                            it.data.toUiModel(myMemberId).toBoardPieces(profileUseCase.getProfile())

                        if (boardPieces.value.isEmpty()) {
                            _boardPieces.emit(boardPieceList)
                        } else {
                            val isMoved =
                                boardPieces.value.find { it.isMe }?.index?.plus(1) == boardPieceList.find { it.isMe }?.index

                            val newList = PieceManager.getBoardPieces(
                                boardPieces.value,
                                boardPieceList
                            )
                            _boardPieces.emit(
                                newList
                            )
                            if (isMoved) {
                                delay(550)
                                _boardRewardEvent.emit(
                                    it.data.missionBoards.find { it.isMyPosition }?.reward
                                )
                            }
                        }
                    }

                    else -> {
                        _missionBoardUiModel.emit(MissionBoardUiModel.Error)
                        handleMissionError(isSameAsLastMission())
                    }
                }
            }
    }

    private suspend fun getMission() {
        getMissionUseCase(missionId).catch {
            _missionUiModel.emit(MissionUiModel.Error)
        }.collect {
            when (it) {
                is DomainResult.Success -> {
                    _missionUiModel.emit(MissionUiModel.Success(it.data))
                }

                else -> {
                    _missionUiModel.emit(MissionUiModel.Error)
                    handleMissionError(isSameAsLastMission())
                }
            }
        }
    }

    private suspend fun getMissionVerification() {
        getMissionVerificationsUseCase(missionId).catch {
            _missionVerificationUiModel.emit(MissionVerificationUiModel.Error)
        }.collect {
            when (it) {
                is DomainResult.Success -> {
                    _missionVerificationUiModel.emit(MissionVerificationUiModel.Success(it.data))
                }

                else -> {
                    _missionVerificationUiModel.emit(MissionVerificationUiModel.Error)
                    handleMissionError(isSameAsLastMission())
                }
            }
        }
    }

    fun deleteMission() {
        viewModelScope.launch {
            deleteMissionUseCase(missionId)
                .catch {
                    _deleteMissionResultEvent.emit(false)
                }.collect {
                    _deleteMissionResultEvent.emit(true)
                }
        }
    }

    fun setViewedTooltip() {
        viewModelScope.launch {
            setViewedTooltipUseCase().collect()
        }
    }

    fun getMyMissionVerification(
        number: Int
    ) {
        viewModelScope.launch {
            getMyMissionVerificationUseCase(
                missionId,
                number
            ).catch {

            }.collect {
                when (it) {
                    is DomainResult.Success -> {
                        _myMissionVerification.emit(
                            UserStory(
                                characterUiModelType = it.data.characterType.toCharacterUiModel(),
                                imageUrl = it.data.imageUrl,
                                isVerified = true,
                                nickname = it.data.nickname,
                                verifiedAt = it.data.verifiedAt,
                                viewedAt = it.data.viewedAt,
                                missionVerificationId = it.data.missionVerificationId
                            )
                        )
                    }

                    else -> {

                    }
                }
            }
        }
    }

    fun viewVerification(missionVerificationId: Long) {
        viewModelScope.launch {
            viewVerificationUseCase(missionVerificationId)
                .collectLatest {
                    getMissionVerification()
                }
        }
    }

    fun resetMissionError() {
        _missionError.value = null
    }

    private suspend fun handleMissionError(isSameAsLastMission : Boolean){
        if(isSameAsLastMission) _missionError.emit(MissionError.INVALID_MISSION)
        else _missionError.emit(MissionError.NOT_EXIST)
    }

    private suspend fun isSameAsLastMission(): Boolean {
        return when (val result = getJoinedMissionsUseCase(filter = statusString).firstOrNull()) {
            is DomainResult.Success -> {
                result.data.missions.lastOrNull()?.missionId == missionId
            }

            else -> false
        }
    }
}
