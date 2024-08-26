package com.goalpanzi.mission_mate.feature.onboarding.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.core.designsystem.theme.component.MissionMateTopAppBar
import com.goalpanzi.mission_mate.core.designsystem.theme.component.NavigationType
import com.goalpanzi.mission_mate.feature.onboarding.R

@Composable
fun BoardSetupNavigationBar(
    onBackClick: () -> Unit,
    currentStep: () -> Int,
    modifier: Modifier = Modifier,
    maxStep: Int = 3,
) {
    Column(
        modifier = modifier
    ) {
        MissionMateTopAppBar(
            modifier = modifier,
            navigationType = NavigationType.BACK,
            onNavigationClick = onBackClick,
            containerColor = ColorWhite_FFFFFFFF
        )
        Row(
            modifier = Modifier.padding(start = 16.dp,bottom = 16.dp, end = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(
                    id = when (currentStep()) {
                        1 -> R.string.onboarding_board_setup_mission_title
                        2 -> R.string.onboarding_board_setup_schedule_title
                        else -> R.string.onboarding_board_setup_verification_time_title
                    }
                ),
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp),
                style = MissionMateTypography.heading_sm_bold,
                color = ColorGray1_FF404249
            )
            OutlinedTextBox(
                text = "${currentStep()}/$maxStep",
                textStyle = MissionMateTypography.body_lg_regular
            )
        }
    }
}