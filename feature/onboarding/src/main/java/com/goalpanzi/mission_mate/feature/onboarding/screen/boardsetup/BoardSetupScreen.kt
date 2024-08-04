package com.goalpanzi.mission_mate.feature.onboarding.screen.boardsetup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateButtonType
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateTextButton
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray2_FF4F505C
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorOrange_FFFF5732
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.feature.onboarding.R
import com.goalpanzi.mission_mate.feature.onboarding.component.BoardSetupNavigationBar
import com.goalpanzi.mission_mate.feature.onboarding.component.DatePickerDialog
import com.goalpanzi.mission_mate.feature.onboarding.model.VerificationTimeType
import com.goalpanzi.mission_mate.feature.onboarding.screen.boardsetup.BoardSetupViewModel.Companion.BoardSetupStep
import com.goalpanzi.mission_mate.feature.onboarding.util.DateUtils.dateToString
import com.goalpanzi.mission_mate.feature.onboarding.util.DateUtils.filterDatesByDayOfWeek
import com.goalpanzi.mission_mate.feature.onboarding.util.styledTextWithHighlights
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun BoardSetupRoute(
    onSuccess : () -> Unit,
    onBackClick: () -> Unit,
    viewModel: BoardSetupViewModel = hiltViewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val localFocusManager = LocalFocusManager.current
    val pagerState = rememberPagerState { BoardSetupStep.entries.size }

    val startDate by viewModel.startDate.collectAsStateWithLifecycle()
    val endDate by viewModel.endDate.collectAsStateWithLifecycle()
    val selectedDays by viewModel.selectedDays.collectAsStateWithLifecycle()
    val selectedVerificationTimeType by viewModel.selectedVerificationTimeType.collectAsStateWithLifecycle()

    val enabledDaysOfWeek by viewModel.enabledDaysOfWeek.collectAsStateWithLifecycle()
    val enabledButton by viewModel.enabledButton.collectAsStateWithLifecycle()
    val currentStep by viewModel.currentStep.collectAsStateWithLifecycle()

    var isShownStartDateDialog by remember { mutableStateOf(false) }
    var isShownEndDateDialog by remember { mutableStateOf(false) }

    if (isShownStartDateDialog) {
        DatePickerDialog(
            selectedDate = startDate,
            onSuccess = {
                viewModel.updateStartDate(it)
                isShownStartDateDialog = !isShownStartDateDialog
            },
            selectableStartDate = LocalDate.now().plusDays(1),
            selectableEndDate = null,
            onDismiss = { isShownStartDateDialog = !isShownStartDateDialog }
        )
    }

    if (isShownEndDateDialog) {
        DatePickerDialog(
            selectedDate = endDate,
            onSuccess = {
                viewModel.updateEndDate(it)
                isShownEndDateDialog = !isShownEndDateDialog
            },
            selectableStartDate = startDate,
            selectableEndDate = startDate?.plusDays(29),
            onDismiss = { isShownEndDateDialog = !isShownEndDateDialog }
        )
    }

    LaunchedEffect(currentStep) {
        if(currentStep != BoardSetupStep.MISSION){
            localFocusManager.clearFocus()
            keyboardController?.hide()
        }
        if(currentStep.ordinal in 0 until pagerState.pageCount)
            pagerState.animateScrollToPage(currentStep.ordinal)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.setupSuccessEvent.collect {
            onSuccess()
        }
    }

    BoardSetupScreen(
        currentStep = currentStep,
        missionTitle = viewModel.missionTitle,
        startDate = startDate?.let {
            dateToString(it)
        } ?: "",
        endDate = endDate?.let {
            dateToString(it)
        } ?: "",
        selectedDays = selectedDays,
        count = filterDatesByDayOfWeek(startDate, endDate, selectedDays),
        selectedVerificationTimeType = selectedVerificationTimeType,
        enabledDaysOfWeek = enabledDaysOfWeek,
        enabledButton = enabledButton,
        pagerState = pagerState,
        onClickNextStep = viewModel::updateCurrentStepToNext,
        onMissionTitleChange = viewModel::updateMissionTitle,
        onClickStartDate = {
            isShownStartDateDialog = true
        },
        onClickEndDate = {
            isShownEndDateDialog = true
        },
        onClickDayOfWeek = viewModel::updateSelectedDays,
        onClickVerificationTimeType = viewModel::updateSelectedVerificationTimeType,
        onBackClick = {
            when(currentStep){
                BoardSetupStep.MISSION -> {
                    onBackClick()
                }
                else -> {
                    viewModel.updateCurrentStepToBack()
                }
            }
        }
    )
}

@Composable
fun BoardSetupScreen(
    currentStep: BoardSetupStep,
    missionTitle: String,
    startDate: String,
    endDate: String,
    selectedDays : List<DayOfWeek>,
    count : Int,
    selectedVerificationTimeType: VerificationTimeType?,
    enabledDaysOfWeek : Set<DayOfWeek>,
    enabledButton: Boolean,
    pagerState : PagerState,
    onClickNextStep: () -> Unit,
    onMissionTitleChange: (String) -> Unit,
    onClickStartDate: () -> Unit,
    onClickEndDate: () -> Unit,
    onClickDayOfWeek : (DayOfWeek) -> Unit,
    onClickVerificationTimeType : (VerificationTimeType) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ColorWhite_FFFFFFFF)
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        BoardSetupNavigationBar(
            onBackClick = onBackClick,
            currentStep = {
                currentStep.ordinal + 1
            }
        )

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = pagerState,
            userScrollEnabled = false
        ) {
            when (it) {
                0 -> {
                    BoardSetupMission(
                        missionTitle = missionTitle,
                        onTitleChange = onMissionTitleChange,
                    )
                }

                1 -> {
                    BoardSetupSchedule(
                        startDate = startDate,
                        endDate = endDate,
                        count = "$count".padStart(2,'0'),
                        enabledDaysOfWeek = enabledDaysOfWeek,
                        onClickStartDate = onClickStartDate,
                        onClickEndDate = onClickEndDate,
                        selectedDays = selectedDays,
                        onSelectDay = onClickDayOfWeek
                    )
                }

                2 -> {
                    BoardSetupVerificationTime(
                        selectedTimeType = selectedVerificationTimeType,
                        onClickTime = onClickVerificationTimeType
                    )
                }
            }
        }
        MissionMateTextButton(
            modifier = Modifier
                .padding(vertical = 36.dp, horizontal = 24.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            buttonType = if (enabledButton) MissionMateButtonType.ACTIVE else MissionMateButtonType.DISABLED,
            textId = if (currentStep.ordinal == 2) R.string.done else R.string.next,
            onClick = {
                onClickNextStep()
            }
        )
    }
}

@Composable
fun ColumnScope.BoardSetupDescription(
    text: String,
    colorTargetTexts: List<String>
) {
    Text(
        modifier = Modifier.padding(top = 6.dp, bottom = 60.dp),
        text = styledTextWithHighlights(
            text = text,
            colorTargetTexts = colorTargetTexts,
            textColor = ColorGray2_FF4F505C,
            targetTextColor = ColorOrange_FFFF5732,
            targetFontWeight = FontWeight.Bold,
        ),
        style = MissionMateTypography.title_xl_regular
    )
}

@Composable
fun ColumnScope.BoardSetupDescription(
    text: String,
    count : String,
    colorTargetTexts: List<String>
) {
    Text(
        modifier = Modifier.padding(top = 6.dp, bottom = 60.dp),
        text = styledTextWithHighlights(
            text = text,
            colorTargetTexts = colorTargetTexts,
            weightTargetTexts = listOf(count),
            underlineTargetTexts = listOf(count),
            textColor = ColorGray2_FF4F505C,
            targetTextColor = ColorOrange_FFFF5732,
            targetFontWeight = FontWeight.Bold,
        ),
        style = MissionMateTypography.title_xl_regular
    )
}