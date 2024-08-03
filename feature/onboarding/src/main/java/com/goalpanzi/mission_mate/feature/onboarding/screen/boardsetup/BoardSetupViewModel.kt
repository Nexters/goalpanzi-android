package com.goalpanzi.mission_mate.feature.onboarding.screen.boardsetup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goalpanzi.mission_mate.feature.onboarding.model.VerificationTimeType
import com.goalpanzi.mission_mate.feature.onboarding.util.DateUtils.longToLocalDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import kotlin.math.absoluteValue

@HiltViewModel
class BoardSetupViewModel @Inject constructor(

) : ViewModel() {

    private val _setupSuccessEvent = MutableSharedFlow<Unit>()
    val setupSuccessEvent: SharedFlow<Unit> = _setupSuccessEvent.asSharedFlow()

    private val _currentStep = MutableStateFlow(BoardSetupStep.MISSION)
    val currentStep: StateFlow<BoardSetupStep> = _currentStep.asStateFlow()

    var missionTitle by mutableStateOf("")
        private set

    private val _startDate = MutableStateFlow<LocalDate?>(null)
    val startDate: StateFlow<LocalDate?> = _startDate.asStateFlow()

    private val _endDate = MutableStateFlow<LocalDate?>(null)
    val endDate: StateFlow<LocalDate?> = _endDate.asStateFlow()

    private val _selectedDays = MutableStateFlow<List<DayOfWeek>>(emptyList())
    val selectedDays: StateFlow<List<DayOfWeek>> = _selectedDays.asStateFlow()

    private val _selectedVerificationTimeType = MutableStateFlow<VerificationTimeType?>(null)
    val selectedVerificationTimeType: StateFlow<VerificationTimeType?> =
        _selectedVerificationTimeType.asStateFlow()

    val enabledDaysOfWeek: StateFlow<Set<DayOfWeek>> =
        combine(
            startDate,
            endDate
        ) { startDate, endDate ->
            if (startDate == null || endDate == null) {
                emptySet()
            } else if (ChronoUnit.DAYS.between(startDate, endDate).absoluteValue >= 7) {
                DayOfWeek.entries.toSet()
            } else {
                generateSequence(startDate) { date ->
                    if (date.isBefore(endDate)) date.plusDays(1) else null
                }.map { it.dayOfWeek }
                    .toSet()
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = emptySet()
        )

    val enabledButton: StateFlow<Boolean> =
        combine(
            currentStep,
            snapshotFlow { missionTitle },
            enabledDaysOfWeek,
            selectedDays,
            selectedVerificationTimeType
        ) { step, title, enabledDaysOfWeek, selectedDays, selectedVerificationTimeType ->
            when (step) {
                BoardSetupStep.MISSION -> {
                    title.length in MISSION_TITLE_MIN_LENGTH..MISSION_TITLE_MAX_LENGTH
                }

                BoardSetupStep.SCHEDULE -> {
                    enabledDaysOfWeek.isNotEmpty() && selectedDays.isNotEmpty()
                }

                BoardSetupStep.VERIFICATION_TIME -> {
                    selectedVerificationTimeType != null
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = false
        )

    fun updateCurrentStepToNext() {
        viewModelScope.launch {
            if (currentStep.value.ordinal in 0 until BoardSetupStep.entries.lastIndex) {
                _currentStep.emit(
                    BoardSetupStep.entries.get(currentStep.value.ordinal + 1)
                )
            } else if (currentStep.value == BoardSetupStep.VERIFICATION_TIME) {
                _setupSuccessEvent.emit(Unit)
            }
        }
    }

    fun updateCurrentStepToBack() {
        viewModelScope.launch {
            if (currentStep.value.ordinal in 1 .. BoardSetupStep.entries.lastIndex) {
                _currentStep.emit(
                    BoardSetupStep.entries.get(currentStep.value.ordinal - 1)
                )
            }
        }
    }

    fun updateMissionTitle(title: String) {
        if (title.length <= MISSION_TITLE_MAX_LENGTH) missionTitle = title
    }

    fun updateStartDate(date: Long) {
        viewModelScope.launch {
            _startDate.emit(longToLocalDate(date))
            endDate.value?.let { endDate ->
                if(longToLocalDate(date).isAfter(endDate)) _endDate.emit(null)
            }

        }
    }

    fun updateEndDate(date: Long) {
        viewModelScope.launch {
            _endDate.emit(longToLocalDate(date))
        }
    }

    fun updateSelectedDays(targetDay: DayOfWeek) {
        viewModelScope.launch {
            _selectedDays.update { days ->
                if (days.contains(targetDay)) {
                    days.filter { it != targetDay }
                } else days + targetDay
            }
        }
    }

    fun updateSelectedVerificationTimeType(timeType: VerificationTimeType) {
        viewModelScope.launch {
            _selectedVerificationTimeType.emit(timeType)
        }
    }


    companion object {

        const val MISSION_TITLE_MIN_LENGTH = 4
        const val MISSION_TITLE_MAX_LENGTH = 12


        enum class BoardSetupStep {
            MISSION, SCHEDULE, VERIFICATION_TIME
        }
    }
}