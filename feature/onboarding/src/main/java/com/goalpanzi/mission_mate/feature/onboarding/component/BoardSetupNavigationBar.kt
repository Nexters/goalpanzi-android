package com.goalpanzi.mission_mate.feature.onboarding.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
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
        IconButton(
            modifier = Modifier.padding(start = 4.dp),
            onClick = onBackClick
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.KeyboardArrowLeft, // merge 전까지 임시 사용
                contentDescription = null
            )
        }
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