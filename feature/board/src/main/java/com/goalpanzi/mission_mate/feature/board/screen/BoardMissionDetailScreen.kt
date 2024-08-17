package com.goalpanzi.mission_mate.feature.board.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray2_FF4F505C
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray3_FF727484
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray4_FFE5E5E5
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorOrange_FFFF5732
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorRed_FFFF5858
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.core.designsystem.theme.component.MissionMateTopAppBar
import com.goalpanzi.mission_mate.core.designsystem.theme.component.NavigationType
import com.goalpanzi.mission_mate.feature.board.R
import com.goalpanzi.mission_mate.feature.board.component.RequestDeleteMissionDialog
import com.goalpanzi.mission_mate.feature.onboarding.model.VerificationTimeType
import com.goalpanzi.mission_mate.feature.onboarding.util.styledTextWithHighlights

@Composable
fun BoardMissionDetailRoute(
    onClickDelete : () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isShownRequestDeleteMissionDialog by remember { mutableStateOf(false) }
    if(isShownRequestDeleteMissionDialog){
        RequestDeleteMissionDialog(
            onDismissRequest = {
                isShownRequestDeleteMissionDialog = false
            },
            onClickOk = {

            }
        )
    }

    BoardMissionDetailScreen(
        modifier = modifier,
        boardCount = 12,
        missionTitle = "매일 유산소 1시간",
        missionPeriod = "2024.07.24~2024.08.14",
        missionDays = listOf("월", "수"),
        missionTime = VerificationTimeType.MORNING,
        onClickDelete = {
            isShownRequestDeleteMissionDialog = !isShownRequestDeleteMissionDialog
        },
        onBackClick = onBackClick
    )
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun BoardMissionDetailScreen(
    boardCount: Int,
    missionTitle: String,
    missionPeriod: String,
    missionDays: List<String>,
    missionTime: VerificationTimeType,
    onClickDelete : () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ColorWhite_FFFFFFFF)
            .statusBarsPadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MissionMateTopAppBar(
            navigationType = NavigationType.BACK,
            onNavigationClick = onBackClick,
            containerColor = ColorWhite_FFFFFFFF
        )
        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = stringResource(id = R.string.board_mission_detail_title),
            style = MissionMateTypography.heading_sm_bold,
            color = ColorGray1_FF404249
        )
        Text(
            modifier = Modifier.padding(
                bottom = 20.dp
            ),
            text = styledTextWithHighlights(
                text = stringResource(id = R.string.board_mission_detail_description, boardCount),
                colorTargetTexts = listOf(
                    stringResource(
                        id = R.string.board_mission_detail_description_color_target,
                        boardCount
                    )
                ),
                targetTextColor = ColorOrange_FFFF5732,
                textColor = ColorGray2_FF4F505C
            ),
            style = MissionMateTypography.body_xl_regular,
            textAlign = TextAlign.Center
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 20.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(id = com.goalpanzi.mission_mate.feature.onboarding.R.string.onboarding_board_setup_mission_input_title),
                color = ColorGray3_FF727484,
                style = MissionMateTypography.body_md_regular
            )
            Row(
                modifier = Modifier.align(Alignment.Start),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentHeight()
                        .weight(1f),
                    text = missionTitle,
                    color = ColorGray1_FF404249,
                    style = MissionMateTypography.body_lg_bold
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp).clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = onClickDelete
                    ),
                    text = stringResource(id = R.string.board_mission_detail_delete),
                    color = ColorRed_FFFF5858,
                    style = MissionMateTypography.body_lg_regular
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = ColorGray4_FFE5E5E5
            )

            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(id = com.goalpanzi.mission_mate.feature.onboarding.R.string.onboarding_board_setup_schedule_period_input_title),
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

            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(id = com.goalpanzi.mission_mate.feature.onboarding.R.string.onboarding_invitation_dialog_schedule_day_title),
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

            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(id = com.goalpanzi.mission_mate.feature.onboarding.R.string.onboarding_board_setup_verification_time_input_title),
                color = ColorGray3_FF727484,
                style = MissionMateTypography.body_md_regular
            )
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(id = missionTime.titleId).replace("\n", " "),
                color = ColorGray1_FF404249,
                style = MissionMateTypography.body_lg_bold
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = ColorGray4_FFE5E5E5
            )

        }
    }
}

@Preview
@Composable
private fun PreviewBoardMissionDetailRoute() {
    BoardMissionDetailRoute(
        onBackClick = {},
        onClickDelete = {}
    )

}