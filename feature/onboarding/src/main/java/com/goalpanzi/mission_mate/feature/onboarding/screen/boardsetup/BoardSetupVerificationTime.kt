package com.goalpanzi.mission_mate.feature.onboarding.screen.boardsetup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray3_FF727484
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray5_FFF5F6F9
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.feature.onboarding.R
import com.goalpanzi.mission_mate.feature.onboarding.model.VerificationTimeType

@Composable
fun BoardSetupVerificationTime(
    selectedTimeType : VerificationTimeType?,
    onClickTime : (VerificationTimeType) -> Unit,
    modifier : Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        BoardSetupDescription(
            text = stringResource(id = R.string.onboarding_board_setup_verification_time_description),
            colorTargetTexts = listOf(
                stringResource(R.string.onboarding_board_setup_verification_time_color_target),
            )
        )
        VerificationTime(
            modifier = Modifier.fillMaxWidth(),
            selectedTime = selectedTimeType,
            onClick = onClickTime
        )
    }
}



@Composable
fun VerificationTime(
    modifier: Modifier = Modifier,
    selectedTime: VerificationTimeType?,
    onClick: (VerificationTimeType) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(id = R.string.onboarding_board_setup_verification_time_input_title),
            style = MissionMateTypography.body_md_bold,
            color = ColorGray3_FF727484
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            VerificationTimeType.entries.forEach {
                Button(
                    modifier = Modifier
                        .height(84.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedTime == it) ColorGray1_FF404249
                        else ColorGray5_FFF5F6F9
                    ),
                    onClick = {
                        onClick(it)
                    },
                    contentPadding = PaddingValues()
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = it.titleId),
                            color = if (selectedTime != it) ColorGray3_FF727484
                            else ColorWhite_FFFFFFFF,
                            textAlign = TextAlign.Center,
                            style = MissionMateTypography.body_lg_regular
                        )

                    }
                }
            }
        }

    }
}