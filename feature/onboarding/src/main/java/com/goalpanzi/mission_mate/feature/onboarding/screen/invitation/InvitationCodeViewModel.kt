package com.goalpanzi.mission_mate.feature.onboarding.screen.invitation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goalpanzi.mission_mate.core.domain.common.DomainResult
import com.goalpanzi.mission_mate.core.domain.mission.usecase.SetMissionJoinedUseCase
import com.goalpanzi.mission_mate.core.domain.onboarding.usecase.GetMissionByInvitationCodeUseCase
import com.goalpanzi.mission_mate.core.domain.onboarding.usecase.JoinMissionUseCase
import com.goalpanzi.mission_mate.feature.onboarding.model.CodeResultEvent
import com.goalpanzi.mission_mate.feature.onboarding.model.InvitationCode
import com.goalpanzi.mission_mate.feature.onboarding.model.JoinResultEvent
import com.goalpanzi.mission_mate.feature.onboarding.model.toMissionUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvitationCodeViewModel @Inject constructor(
    private val getMissionByInvitationCodeUseCase: GetMissionByInvitationCodeUseCase,
    private val joinMissionUseCase: JoinMissionUseCase,
    private val setMissionJoinedUseCase: SetMissionJoinedUseCase
) : ViewModel() {

    var invitationCode by mutableStateOf(InvitationCode.create())
        private set

    private val _isNotCodeValid = MutableStateFlow(false)
    val isNotCodeValid: StateFlow<Boolean> = _isNotCodeValid.asStateFlow()

    private val _isErrorToastEvent = MutableSharedFlow<String>()
    val isErrorToastEvent: SharedFlow<String> = _isErrorToastEvent.asSharedFlow()

    private val _codeInputActionEvent = MutableSharedFlow<CodeActionEvent>()
    val codeInputActionEvent: SharedFlow<CodeActionEvent> = _codeInputActionEvent.asSharedFlow()

    private val _codeResultEvent = MutableSharedFlow<CodeResultEvent>()
    val codeResultEvent: SharedFlow<CodeResultEvent> = _codeResultEvent.asSharedFlow()

    private val _joinResultEvent = MutableSharedFlow<JoinResultEvent>()
    val joinResultEvent: SharedFlow<JoinResultEvent> = _joinResultEvent.asSharedFlow()

    fun inputCode(index: Int, value: String) {
        if (isNotCodeValid.value) resetCodeValidState()
        invitationCode = invitationCode.input(value, index)
        if (index == 0 && value.isBlank()) return
        viewModelScope.launch {
            _codeInputActionEvent.emit(
                CodeActionEvent.from(index,value)
            )
        }
    }

    fun deleteCode(index: Int) {
        invitationCode = invitationCode.deleteAt(index)
    }

    fun checkCode() {
        viewModelScope.launch {
            getMissionByInvitationCodeUseCase(invitationCode.getCode())
                .catch {
                    _codeResultEvent.emit(CodeResultEvent.Error)
                }.collect { result ->
                    when (result) {
                        is DomainResult.Success -> {
                            _codeResultEvent.emit(
                                CodeResultEvent.Success(result.data.toMissionUiModel())
                            )
                        }

                        is DomainResult.Error -> {
                            result.message?.let {
                                if (it.contains("CAN_NOT_JOIN_MISSION")) {
                                    _isErrorToastEvent.emit("CAN_NOT_JOIN_MISSION")
                                } else if (it.contains("EXCEED_MAX_PERSONNEL")) {
                                    _isErrorToastEvent.emit("EXCEED_MAX_PERSONNEL")
                                } else {
                                    _isNotCodeValid.emit(true)
                                }
                            }
                        }

                        else -> {
                            _isNotCodeValid.emit(true)
                        }
                    }
                }
        }
    }

    fun joinMission(
        missionId: Long
    ) {
        viewModelScope.launch {
            joinMissionUseCase(
                invitationCode.getCode()
            ).catch {

            }.collect {
                setMissionJoinedUseCase(true).collect()

                _joinResultEvent.emit(
                    JoinResultEvent.Success(missionId)
                )
            }
        }
    }

    private fun resetCodeValidState() {
        viewModelScope.launch {
            _isNotCodeValid.emit(false)
        }
    }
}

enum class CodeActionEvent {
    NEXT,
    PREVIOUS,
    DONE;

    companion object {
        fun from(index: Int, value: String) : CodeActionEvent {
            return if (value.length == 1) {
                when (index) {
                    0, 1, 2 -> NEXT
                    else -> DONE
                }
            } else if (value.isBlank()) {
                PREVIOUS
            } else {
                when (index + value.length) {
                    1, 2, 3 -> NEXT
                    else -> DONE
                }
            }
        }
    }
}
