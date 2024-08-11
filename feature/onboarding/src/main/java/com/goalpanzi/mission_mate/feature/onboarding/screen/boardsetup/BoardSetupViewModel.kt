package com.goalpanzi.mission_mate.feature.onboarding.screen.boardsetup

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goalpanzi.mission_mate.core.domain.usecase.CreateMissionUseCase
import com.goalpanzi.mission_mate.feature.onboarding.model.BoardSetupResult
import com.goalpanzi.mission_mate.feature.onboarding.model.VerificationTimeType
import com.goalpanzi.mission_mate.feature.onboarding.util.DateUtils.filterDatesByDayOfWeek
import com.goalpanzi.mission_mate.feature.onboarding.util.DateUtils.formatLocalDateToString
import com.goalpanzi.mission_mate.feature.onboarding.util.DateUtils.isDifferenceTargetDaysOrMore
import com.goalpanzi.mission_mate.feature.onboarding.util.DateUtils.longToLocalDate
import com.luckyoct.core.model.base.NetworkResult
import com.luckyoct.core.model.request.CreateMissionRequest
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
import javax.inject.Inject

@HiltViewModel
class BoardSetupViewModel @Inject constructor(
    private val createMissionUseCase : CreateMissionUseCase
) : ViewModel() {

    private val _setupEvent = MutableSharedFlow<BoardSetupResult>()
    val setupEvent: SharedFlow<BoardSetupResult> = _setupEvent.asSharedFlow()

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
            } else if (isDifferenceTargetDaysOrMore(startDate,endDate)) {
                DayOfWeek.entries.toSet()
            } else {
                getUniqueDaysOfWeekInRange(startDate,endDate)
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
                    BoardSetupStep.entries[currentStep.value.ordinal + 1]
                )
            } else if (currentStep.value == BoardSetupStep.VERIFICATION_TIME) {
                createMission()
            }
        }
    }

    fun updateCurrentStepToBack() {
        viewModelScope.launch {
            if (currentStep.value.ordinal in 1 .. BoardSetupStep.entries.lastIndex) {
                _currentStep.emit(
                    BoardSetupStep.entries[currentStep.value.ordinal - 1]
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

    private suspend fun createMission(){
        val timeOfDay = selectedVerificationTimeType.value?.name
        val startDate = startDate.value
        val endDate = endDate.value
        if(timeOfDay == null || startDate == null || endDate == null){
            _setupEvent.emit(BoardSetupResult.Error("Board Setup is Failed"))
            return
        }

        createMissionUseCase(
            CreateMissionRequest(
                description = missionTitle,
                missionStartDate = formatLocalDateToString(startDate),
                missionEndDate = formatLocalDateToString(endDate),
                missionDays = selectedDays.value.map { it.name },
                timeOfDay = timeOfDay,
                boardCount = filterDatesByDayOfWeek(startDate, endDate, selectedDays.value)
            )
        ).collect { result ->
            when(result){
                is NetworkResult.Success -> {
                    _setupEvent.emit(BoardSetupResult.Success(result.data))
                }
                else -> {
                    _setupEvent.emit(BoardSetupResult.Error("Board Setup is Failed"))
                }
            }
        }
    }

    private fun getUniqueDaysOfWeekInRange(
        startDate : LocalDate,
        endDate: LocalDate
    ) : Set<DayOfWeek> {
        return generateSequence(startDate) { date ->
            if (date.isBefore(endDate)) date.plusDays(1) else null
        }.map { it.dayOfWeek }
            .toSet()
    }

    companion object {

        const val MISSION_TITLE_MIN_LENGTH = 4
        const val MISSION_TITLE_MAX_LENGTH = 12


        enum class BoardSetupStep {
            MISSION, SCHEDULE, VERIFICATION_TIME
        }
    }
}