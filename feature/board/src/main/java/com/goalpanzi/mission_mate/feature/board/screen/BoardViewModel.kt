package com.goalpanzi.mission_mate.feature.board.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goalpanzi.mission_mate.core.domain.usecase.DeleteMissionUseCase
import com.goalpanzi.mission_mate.core.domain.usecase.GetMissionBoardsUseCase
import com.goalpanzi.mission_mate.core.domain.usecase.GetMissionUseCase
import com.goalpanzi.mission_mate.core.domain.usecase.GetMissionVerificationsUseCase
import com.goalpanzi.mission_mate.feature.board.model.MissionBoardUiModel
import com.goalpanzi.mission_mate.feature.board.model.MissionUiModel
import com.goalpanzi.mission_mate.feature.board.model.MissionVerificationUiModel
import com.luckyoct.core.model.base.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val getMissionUseCase: GetMissionUseCase,
    private val getMissionBoardsUseCase: GetMissionBoardsUseCase,
    private val getMissionVerificationsUseCase: GetMissionVerificationsUseCase,
    private val deleteMissionUseCase: DeleteMissionUseCase
) : ViewModel() {

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

    fun getMissionBoards(missionId: Long) {
        viewModelScope.launch {
            _missionBoardUiModel.emit(MissionBoardUiModel.Loading)

            getMissionBoardsUseCase(missionId)
                .catch {
                    _missionBoardUiModel.emit(MissionBoardUiModel.Error)
                }.collect {
                    when (it) {
                        is NetworkResult.Success -> {
                            _missionBoardUiModel.emit(MissionBoardUiModel.Success(it.data))
                        }

                        else -> {
                            _missionBoardUiModel.emit(MissionBoardUiModel.Error)
                        }
                    }

                }
        }
    }

    fun getMission(missionId: Long) {
        viewModelScope.launch {
            getMissionUseCase(missionId).catch {
                _missionUiModel.emit(MissionUiModel.Error)
            }.collect {
                when (it) {
                    is NetworkResult.Success -> {
                        _missionUiModel.emit(MissionUiModel.Success(it.data))
                    }

                    else -> {
                        _missionUiModel.emit(MissionUiModel.Error)
                    }
                }
            }
        }
    }

    fun getMissionVerification(missionId: Long) {
        viewModelScope.launch {
            getMissionVerificationsUseCase(missionId).catch {
                _missionVerificationUiModel.emit(MissionVerificationUiModel.Error)
            }.collect {
                when (it) {
                    is NetworkResult.Success -> {
                        _missionVerificationUiModel.emit(MissionVerificationUiModel.Success(it.data))
                    }

                    else -> {
                        _missionVerificationUiModel.emit(MissionVerificationUiModel.Error)
                    }
                }
            }
        }
    }

    fun deleteMission(missionId: Long) {
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