package com.goalpanzi.mission_mate.feature.onboarding.screen.boardsetup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateTextFieldGroup
import com.goalpanzi.mission_mate.feature.onboarding.R

@Composable
fun BoardSetupMission(
    missionTitle: String,
    isNotTitleValid : Boolean,
    onTitleChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp)
    ) {
        BoardSetupDescription(
            text = stringResource(id = R.string.onboarding_board_setup_mission_description),
            colorTargetTexts = listOf(
                stringResource(R.string.onboarding_board_setup_mission_description_color_target1),
                stringResource(R.string.onboarding_board_setup_mission_description_color_target2)
            )
        )
        MissionMateTextFieldGroup(
            modifier = modifier.fillMaxWidth(),
            text = missionTitle,
            onValueChange = onTitleChange,
            isError = isNotTitleValid,
            useMaxLength = true,
            maxLength = 12,
            titleId = R.string.onboarding_board_setup_mission_input_title,
            hintId = R.string.onboarding_board_setup_mission_input_hint,
            guidanceId = R.string.onboarding_board_setup_mission_input_guide,
        )
    }
}
