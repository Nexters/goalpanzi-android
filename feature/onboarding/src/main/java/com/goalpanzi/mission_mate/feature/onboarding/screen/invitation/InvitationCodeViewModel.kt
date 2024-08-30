package com.goalpanzi.mission_mate.feature.onboarding.screen.invitation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goalpanzi.mission_mate.core.domain.model.base.DomainResult
import com.goalpanzi.mission_mate.core.domain.usecase.mission.GetMissionByInvitationCodeUseCase
import com.goalpanzi.mission_mate.core.domain.usecase.mission.JoinMissionUseCase
import com.goalpanzi.mission_mate.core.domain.usecase.mission.SetMissionJoinedUseCase
import com.goalpanzi.mission_mate.feature.onboarding.model.CodeResultEvent
import com.goalpanzi.mission_mate.feature.onboarding.model.JoinResultEvent
import com.goalpanzi.mission_mate.feature.onboarding.model.toMissionUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvitationCodeViewModel @Inject constructor(
    private val getMissionByInvitationCodeUseCase : GetMissionByInvitationCodeUseCase,
    private val joinMissionUseCase : JoinMissionUseCase,
    private val setMissionJoinedUseCase: SetMissionJoinedUseCase
) : ViewModel() {

    var codeFirst by mutableStateOf("")
        private set

    private val codeFirstFlow =
        snapshotFlow { codeFirst }

    var codeSecond by mutableStateOf("")
        private set

    private val codeSecondFlow =
        snapshotFlow { codeSecond }

    var codeThird by mutableStateOf("")
        private set

    private val codeThirdFlow =
        snapshotFlow { codeThird }

    var codeFourth by mutableStateOf("")
        private set

    private val codeFourthFlow =
        snapshotFlow { codeFourth }

    private val _isNotCodeValid = MutableStateFlow(false)
    val isNotCodeValid: StateFlow<Boolean> = _isNotCodeValid.asStateFlow()

    private val _isErrorToastEvent = MutableSharedFlow<String>()
    val isErrorToastEvent: SharedFlow<String> = _isErrorToastEvent.asSharedFlow()

    val enabledButton: StateFlow<Boolean> =
        combine(
            codeFirstFlow.map { it.isNotEmpty() },
            codeSecondFlow.map { it.isNotEmpty() },
            codeThirdFlow.map { it.isNotEmpty() },
            codeFourthFlow.map { it.isNotEmpty() },
            isNotCodeValid
        ) { isNotFirstEmpty, isNotSecondEmpty, isNotThirdEmpty, isNotFourthEmpty, isNotValid ->
            isNotFirstEmpty && isNotSecondEmpty && isNotThirdEmpty && isNotFourthEmpty && !isNotValid
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = false
        )

    private val _codeInputActionEvent = MutableSharedFlow<CodeActionEvent>()
    val codeInputActionEvent: SharedFlow<CodeActionEvent> = _codeInputActionEvent.asSharedFlow()

    private val _codeResultEvent = MutableSharedFlow<CodeResultEvent>()
    val codeResultEvent: SharedFlow<CodeResultEvent> = _codeResultEvent.asSharedFlow()

    private val _joinResultEvent = MutableSharedFlow<JoinResultEvent>()
    val joinResultEvent: SharedFlow<JoinResultEvent> = _joinResultEvent.asSharedFlow()

    fun updateCodeFirst(code: String) {
        if(code == " ") return
        if (isNotCodeValid.value) resetCodeValidState()
        if (code.length <= 1) codeFirst = code
        viewModelScope.launch {
            _codeInputActionEvent.emit(if(code.isNotEmpty()) CodeActionEvent.FIRST_DONE  else CodeActionEvent.FIRST_CLEAR)
        }
    }

    fun updateCodeSecond(code: String) {
        if(code == " ") return
        if (isNotCodeValid.value) resetCodeValidState()
        if (code.length <= 1) codeSecond = code
        viewModelScope.launch {
            _codeInputActionEvent.emit(if(code.isNotEmpty()) CodeActionEvent.SECOND_DONE  else CodeActionEvent.SECOND_CLEAR)
        }
    }

    fun updateCodeThird(code: String) {
        if(code == " ") return
        if (isNotCodeValid.value) resetCodeValidState()
        if (code.length <= 1) codeThird = code
        viewModelScope.launch {
            _codeInputActionEvent.emit(if(code.isNotEmpty()) CodeActionEvent.THIRD_DONE  else CodeActionEvent.THIRD_CLEAR)
        }
    }

    fun updateCodeFourth(code: String) {
        if(code == " ") return
        if (isNotCodeValid.value) resetCodeValidState()
        if (code.length <= 1) codeFourth = code
        viewModelScope.launch {
            _codeInputActionEvent.emit(if(code.isNotEmpty()) CodeActionEvent.FOURTH_DONE else CodeActionEvent.FOURTH_CLEAR)
        }
    }

    fun checkCode() {
        viewModelScope.launch {
            getMissionByInvitationCodeUseCase(
                codeFirst + codeSecond + codeThird + codeFourth
            ).catch {
                _codeResultEvent.emit(CodeResultEvent.Error)
            }.collect { result ->
                when(result){
                    is DomainResult.Success -> {
                        _codeResultEvent.emit(
                            CodeResultEvent.Success(result.data.toMissionUiModel())
                        )
                    }
                    is DomainResult.Error -> {
                        result.message?.let {
                            if(it.contains("CAN_NOT_JOIN_MISSION")){
                                _isErrorToastEvent.emit("CAN_NOT_JOIN_MISSION")
                            }else if(it.contains("EXCEED_MAX_PERSONNEL")){
                                _isErrorToastEvent.emit("EXCEED_MAX_PERSONNEL")
                            }else {
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

    private fun resetCodeValidState() {
        viewModelScope.launch {
            _isNotCodeValid.emit(false)
        }
    }

    fun joinMission(
        missionId : Long
    ){
        viewModelScope.launch {
            joinMissionUseCase(
                codeFirst + codeSecond + codeThird + codeFourth
            ).catch {

            }.collect {
                //
                setMissionJoinedUseCase(true).collect()

                _joinResultEvent.emit(
                    JoinResultEvent.Success(missionId)
                )
            }
        }
    }

    companion object {
        enum class CodeActionEvent {
            FIRST_DONE,
            FIRST_CLEAR,
            SECOND_DONE,
            SECOND_CLEAR,
            THIRD_DONE,
            THIRD_CLEAR,
            FOURTH_DONE,
            FOURTH_CLEAR

        }
    }
}