package com.goalpanzi.mission_mate.feature.onboarding.screen.boardsetup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorBlack_FF000000
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray2_FF4F505C
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray3_FF727484
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray4_FFE5E5E5
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray5_80F5F6F9
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray5_FFF5F6F9
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.feature.onboarding.R
import com.goalpanzi.mission_mate.feature.onboarding.util.getStringId
import java.time.DayOfWeek

@Composable
fun BoardSetupSchedule(
    startDate: String,
    endDate: String,
    selectedDays: List<DayOfWeek>,
    enabledDaysOfWeek: Set<DayOfWeek>,
    count: String,
    onClickStartDate: () -> Unit,
    onClickEndDate: () -> Unit,
    onSelectDay: (DayOfWeek) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        BoardSetupDescription(
            text = stringResource(id = R.string.onboarding_board_setup_schedule_description, count),
            colorTargetTexts = listOf(
                stringResource(R.string.onboarding_board_setup_schedule_description_color_target1),
                stringResource(R.string.onboarding_board_setup_schedule_description_color_target2)
            ),
            count = count + stringResource(id = R.string.onboarding_board_setup_schedule_description_style_target)
        )
        Period(
            startDate = startDate,
            endDate = endDate,
            modifier = Modifier.fillMaxWidth(),
            onClickStartDate = onClickStartDate,
            onClickEndDate = onClickEndDate
        )
        Frequency(
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth(),
            enabledDaysOfWeek = enabledDaysOfWeek,
            selectedDays = selectedDays,
            onClickDay = onSelectDay
        )
    }
}

@Composable
fun Period(
    startDate: String,
    endDate: String,
    modifier: Modifier = Modifier,
    onClickStartDate: () -> Unit,
    onClickEndDate: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.onboarding_board_setup_schedule_period_input_title),
            style = MissionMateTypography.body_md_bold,
            color = ColorGray3_FF727484
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier
                    .height(60.dp)
                    .weight(1f),
                border = if (startDate.isBlank()) null else BorderStroke(1.dp, ColorGray4_FFE5E5E5),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (startDate.isBlank()) ColorGray5_80F5F6F9 else ColorWhite_FFFFFFFF
                ),
                contentPadding = PaddingValues(horizontal = 16.dp),
                onClick = onClickStartDate
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = startDate.ifBlank { stringResource(id = R.string.onboarding_board_setup_schedule_period_start_hint) },
                    color = if (startDate.isBlank()) ColorGray3_FF727484 else ColorGray1_FF404249,
                    style = MissionMateTypography.body_lg_regular
                )
            }
            Text(
                modifier = Modifier.padding(horizontal = 7.dp),
                text = "~",
                color = ColorBlack_FF000000,
                style = MissionMateTypography.body_lg_regular
            )
            Button(
                modifier = Modifier
                    .height(60.dp)
                    .weight(1f),
                border = if (endDate.isBlank()) null else BorderStroke(1.dp, ColorGray4_FFE5E5E5),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (endDate.isBlank()) ColorGray5_80F5F6F9 else ColorWhite_FFFFFFFF
                ),
                contentPadding = PaddingValues(horizontal = 16.dp),
                onClick = onClickEndDate
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = endDate.ifBlank { stringResource(id = R.string.onboarding_board_setup_schedule_period_end_hint) },
                    color = if (endDate.isBlank()) ColorGray3_FF727484 else ColorGray1_FF404249,
                    style = MissionMateTypography.body_lg_regular
                )
            }
        }
        Text(
            text = stringResource(id = R.string.onboarding_board_setup_schedule_period_input_guide),
            style = MissionMateTypography.body_md_regular,
            color = ColorGray2_FF4F505C
        )
    }
}

@Composable
fun Frequency(
    selectedDays: List<DayOfWeek>,
    enabledDaysOfWeek: Set<DayOfWeek>,
    onClickDay: (DayOfWeek) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 4.dp),
            text = stringResource(id = R.string.onboarding_board_setup_schedule_day_input_title),
            style = MissionMateTypography.body_md_bold,
            color = ColorGray3_FF727484.copy(alpha = if (enabledDaysOfWeek.isNotEmpty()) 1f else 0.3f)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.5.dp)
        ) {
            DayOfWeek.entries.forEach {
                DayItem(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    dayOfWeek = it,
                    enabled = enabledDaysOfWeek.contains(it),
                    selected = it in selectedDays,
                    onClick = {
                        onClickDay(it)
                    }
                )
            }
        }
        Text(
            text = stringResource(id = R.string.onboarding_board_setup_schedule_day_input_guide),
            style = MissionMateTypography.body_md_regular,
            color = ColorGray2_FF4F505C.copy(alpha = if (enabledDaysOfWeek.isNotEmpty()) 1f else 0.3f)
        )
    }
}


@Composable
fun DayItem(
    dayOfWeek: DayOfWeek,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = CircleShape,
    selected: Boolean = false
) {
    TextButton(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        shape = shape,
        colors = ButtonDefaults.textButtonColors(
            containerColor = if (selected) ColorGray1_FF404249 else ColorGray5_FFF5F6F9,
            disabledContainerColor = ColorGray5_FFF5F6F9.copy(0.3f)
        ),
        enabled = enabled,
        onClick = onClick,
    ) {
        Text(
            text = stringResource(id = dayOfWeek.getStringId()),
            color = if (selected && enabled) ColorWhite_FFFFFFFF
            else if(enabled) ColorGray1_FF404249 else ColorGray1_FF404249.copy(0.3f),
            style = MissionMateTypography.body_lg_regular,
            textAlign = TextAlign.Center
        )
    }
}