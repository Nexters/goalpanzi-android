package com.goalpanzi.mission_mate.feature.onboarding.screen.invitation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateDialog
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray2_FF4F505C
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray3_FF727484
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray4_FFE5E5E5
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorOrange_FFFF5732
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.feature.onboarding.R
import com.goalpanzi.mission_mate.feature.onboarding.model.VerificationTimeType
import com.goalpanzi.mission_mate.feature.onboarding.util.styledTextWithHighlights

@Composable
fun InvitationDialog(
    count : Int,
    missionTitle : String,
    missionPeriod : String,
    missionDays : List<String>,
    missionTime : VerificationTimeType,
    onDismissRequest: () -> Unit,
    onClickOk: () -> Unit,
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = MissionMateTypography.title_xl_bold,
    descriptionStyle: TextStyle = MissionMateTypography.body_lg_regular,
    okTextStyle: TextStyle = MissionMateTypography.body_lg_bold,
    cancelTextStyle: TextStyle = MissionMateTypography.body_lg_bold
){
    val scrollState = rememberScrollState()

    MissionMateDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        onClickOk = onClickOk,
        okTextId = R.string.check_ok,
        cancelTextId = R.string.check_no,
        okTextStyle = okTextStyle,
        cancelTextStyle = cancelTextStyle
    ){
        Column(
            modifier = Modifier
                .weight(1f, false)
                .padding(bottom = 29.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.onboarding_invitation_dialog_title),
                style = titleStyle,
                textAlign = TextAlign.Center,
                color = ColorGray1_FF404249
            )
            Text(
                modifier = Modifier.padding(top = 4.dp, bottom = 20.dp),
                text = styledTextWithHighlights(
                    text = stringResource(id =  R.string.onboarding_invitation_dialog_description,count),
                    colorTargetTexts = listOf(stringResource(id = R.string.onboarding_invitation_dialog_description_color_target,count)),
                    targetTextColor = ColorOrange_FFFF5732,
                    textColor = ColorGray2_FF4F505C
                ),
                style = descriptionStyle,
                textAlign = TextAlign.Center,
                color = ColorGray2_FF4F505C
            )
            Column(
                modifier = Modifier.verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text (
                    modifier = Modifier.align(Alignment.Start),
                    text = stringResource(id = R.string.onboarding_board_setup_mission_input_title),
                    color = ColorGray3_FF727484,
                    style = MissionMateTypography.body_md_regular
                )
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = missionTitle,
                    color = ColorGray1_FF404249,
                    style = MissionMateTypography.body_lg_bold
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = ColorGray4_FFE5E5E5
                )

                Text (
                    modifier = Modifier.align(Alignment.Start),
                    text = stringResource(id = R.string.onboarding_board_setup_schedule_period_input_title),
                    color = ColorGray3_FF727484,
                    style = MissionMateTypography.body_md_regular
                )
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = missionPeriod,
                    color = ColorGray1_FF404249,
                    style = MissionMateTypography.body_lg_bold
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = ColorGray4_FFE5E5E5
                )

                Text (
                    modifier = Modifier.align(Alignment.Start),
                    text = stringResource(id = R.string.onboarding_invitation_dialog_schedule_day_title),
                    color = ColorGray3_FF727484,
                    style = MissionMateTypography.body_md_regular
                )
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = missionDays.joinToString("/"),
                    color = ColorGray1_FF404249,
                    style = MissionMateTypography.body_lg_bold
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = ColorGray4_FFE5E5E5
                )

                Text (
                    modifier = Modifier.align(Alignment.Start),
                    text = stringResource(id = R.string.onboarding_board_setup_verification_time_input_title),
                    color = ColorGray3_FF727484,
                    style = MissionMateTypography.body_md_regular
                )
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = stringResource(id = missionTime.titleId).replace("\n"," "),
                    color = ColorGray1_FF404249,
                    style = MissionMateTypography.body_lg_bold
                )
            }

        }
    }
}

@Preview
@Composable
fun PreviewInvitationDialog(){
    InvitationDialog(
        count = 12,
        modifier = Modifier.fillMaxWidth(),
        missionTitle = "매일 유산소 1시간",
        missionPeriod = "2024.07.24~2024.08.14",
        missionDays = listOf("월","수"),
        missionTime = VerificationTimeType.MORNING,
        onDismissRequest  = {},
        onClickOk = {}

    )
}