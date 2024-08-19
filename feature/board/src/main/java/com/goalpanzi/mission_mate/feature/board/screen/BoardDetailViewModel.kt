package com.goalpanzi.mission_mate.feature.board.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goalpanzi.mission_mate.core.domain.usecase.DeleteMissionUseCase
import com.goalpanzi.mission_mate.core.domain.usecase.GetCachedMemberIdUseCase
import com.goalpanzi.mission_mate.core.domain.usecase.GetMissionUseCase
import com.goalpanzi.mission_mate.feature.board.model.toModel
import com.goalpanzi.mission_mate.feature.board.model.uimodel.MissionUiModel
import com.luckyoct.core.model.UserProfile
import com.luckyoct.core.model.base.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardDetailViewModel @Inject constructor(
    private val getMissionUseCase: GetMissionUseCase,
    private val deleteMissionUseCase: DeleteMissionUseCase,
    private val getCachedMemberIdUseCase: GetCachedMemberIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val missionId: Long = savedStateHandle.get<Long>("missionId")!!

    private val memberId : StateFlow<Long?> = getCachedMemberIdUseCase().stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(500),
        initialValue = null
    )

    private val _deleteMissionResultEvent = MutableSharedFlow<Boolean>()
    val deleteMissionResultEvent: SharedFlow<Boolean> = _deleteMissionResultEvent.asSharedFlow()

    private val _missionUiModel =
        MutableStateFlow<MissionUiModel>(MissionUiModel.Loading)
    val missionUiModel: StateFlow<MissionUiModel> = _missionUiModel.asStateFlow()

    val isHost : StateFlow<Boolean> =
        combine(
            memberId,
            missionUiModel.filter { it is MissionUiModel.Success }
        ){ id, mission ->
            if(mission !is MissionUiModel.Success) return@combine false
            id == mission.missionDetail.hostMemberId
        }.stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = false
        )

    fun getMission() {
        viewModelScope.launch {
            getMissionUseCase(missionId).catch {
                _missionUiModel.emit(MissionUiModel.Error)
            }.collect {
                when (it) {
                    is NetworkResult.Success -> {
                        _missionUiModel.emit(MissionUiModel.Success(it.data.toModel()))
                    }

                    else -> {
                        _missionUiModel.emit(MissionUiModel.Error)
                    }
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
}