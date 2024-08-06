package com.goalpanzi.mission_mate.feature.onboarding.screen.invitation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goalpanzi.mission_mate.feature.onboarding.model.CodeResultEvent
import com.goalpanzi.mission_mate.feature.onboarding.model.MissionUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InvitationCodeViewModel @Inject constructor(

) : ViewModel() {

    var codeFirst by mutableStateOf("")
        private set

    private val isNotCodeFirstEmpty =
        snapshotFlow { codeFirst }

    var codeSecond by mutableStateOf("")
        private set

    private val isNotCodeSecondEmpty =
        snapshotFlow { codeSecond }

    var codeThird by mutableStateOf("")
        private set

    private val isNotCodeThirdEmpty =
        snapshotFlow { codeThird }

    var codeFourth by mutableStateOf("")
        private set

    private val isNotCodeFourthEmpty =
        snapshotFlow { codeFourth }

    private val _isNotCodeValid = MutableStateFlow(false)
    val isNotCodeValid: StateFlow<Boolean> = _isNotCodeValid.asStateFlow()

    val enabledButton: StateFlow<Boolean> =
        combine(
            isNotCodeFirstEmpty.map { it.isNotEmpty() },
            isNotCodeSecondEmpty.map { it.isNotEmpty() },
            isNotCodeThirdEmpty.map { it.isNotEmpty() },
            isNotCodeFourthEmpty.map { it.isNotEmpty() },
            isNotCodeValid
        ) { isNotFirstEmpty, isNotSecondEmpty, isNotThirdEmpty, isNotFourthEmpty, isNotValid ->
            isNotFirstEmpty && isNotSecondEmpty && isNotThirdEmpty && isNotFourthEmpty && !isNotValid
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = false
        )

    val codeInputActionEvent: SharedFlow<CodeActionEvent> =
        merge(
            isNotCodeFirstEmpty.filterNot { it.isEmpty() }.map { CodeActionEvent.FIRST_DONE },
            isNotCodeSecondEmpty.filterNot { it.isEmpty() }.map { CodeActionEvent.SECOND_DONE },
            isNotCodeThirdEmpty.filterNot { it.isEmpty() }.map { CodeActionEvent.THIRD_DONE }
        ).shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500)
        )


    private val _codeResultEvent = MutableSharedFlow<CodeResultEvent>()
    val codeResultEvent: SharedFlow<CodeResultEvent> = _codeResultEvent.asSharedFlow()

    fun updateCodeFirst(code: String) {
        if (isNotCodeValid.value) resetCodeValidState()
        if (code.length <= 1) codeFirst = code
    }

    fun updateCodeSecond(code: String) {
        if (isNotCodeValid.value) resetCodeValidState()
        if (code.length <= 1) codeSecond = code
    }

    fun updateCodeThird(code: String) {
        if (isNotCodeValid.value) resetCodeValidState()
        if (code.length <= 1) codeThird = code
    }

    fun updateCodeFourth(code: String) {
        if (isNotCodeValid.value) resetCodeValidState()
        if (code.length <= 1) codeFourth = code
    }

    fun checkCode() {
        viewModelScope.launch {
            delay(200)
            // 아래는 모두 삭제 예정
            if (isNotCodeValid.value == false) {
                _codeResultEvent.emit(
                    CodeResultEvent.Success(
                        MissionUiModel(
                            "유산소 1시간",
                            "08.15~09.15",
                            "월/수/금",
                            "오전 00~12시"
                        )
                    )
                )
            } else {
                _isNotCodeValid.emit(isNotCodeValid.value.not())
            }

        }
    }

    private fun resetCodeValidState() {
        viewModelScope.launch {
            _isNotCodeValid.emit(false)
        }
    }

    companion object {
        enum class CodeActionEvent {
            FIRST_DONE,
            SECOND_DONE,
            THIRD_DONE,
            FOURTH_DONE,
        }
    }
}