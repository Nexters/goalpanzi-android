package com.goalpanzi.mission_mate.feature.board.screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.goalpanzi.mission_mate.feature.board.model.uimodel.MissionUiModel
import com.goalpanzi.mission_mate.feature.onboarding.model.VerificationTimeType
import com.goalpanzi.mission_mate.feature.onboarding.util.styledTextWithHighlights
import kotlinx.coroutines.launch

@Composable
fun BoardMissionDetailRoute(
    onNavigateOnboarding : () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BoardDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val missionUiModel by viewModel.missionUiModel.collectAsStateWithLifecycle()
    val isHost by viewModel.isHost.collectAsStateWithLifecycle()
    var isShownRequestDeleteMissionDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        viewModel.getMission()

        launch {
            viewModel.deleteMissionResultEvent.collect {
                if (it) {
                    isShownRequestDeleteMissionDialog = false
                    onNavigateOnboarding()
                }
            }
        }

        launch {
            viewModel.missionError.collect {
                Toast.makeText(context,context.getString(R.string.board_mission_not_exist),Toast.LENGTH_SHORT).show()
                onNavigateOnboarding()
            }
        }

    }

    if(isShownRequestDeleteMissionDialog){
        RequestDeleteMissionDialog(
            onDismissRequest = {
                isShownRequestDeleteMissionDialog = false
            },
            onClickOk = {
                viewModel.deleteMission()
            }
        )
    }
    if(missionUiModel is MissionUiModel.Success){
        val missionDetail = (missionUiModel as MissionUiModel.Success).missionDetail
        BoardMissionDetailScreen(
            modifier = modifier,
            boardCount = missionDetail.boardCount ,
            missionTitle = missionDetail.description,
            missionPeriod = missionDetail.missionPeriod,
            missionDays = missionDetail.missionDaysOfWeekTextLocale  ,
            missionTime = VerificationTimeType.valueOf(missionDetail.timeOfDay),
            isHost = isHost,
            onClickDelete = {
                isShownRequestDeleteMissionDialog = !isShownRequestDeleteMissionDialog
            },
            onBackClick = onBackClick
        )
    }else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            CircularProgressIndicator()
        }
    }

}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun BoardMissionDetailScreen(
    boardCount: Int,
    missionTitle: String,
    missionPeriod: String,
    missionDays: List<String>,
    missionTime: VerificationTimeType,
    isHost : Boolean,
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
                if(isHost){
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                                onClick = onClickDelete
                            ),
                        text = stringResource(id = R.string.board_mission_detail_delete),
                        color = ColorRed_FFFF5858,
                        style = MissionMateTypography.body_lg_regular
                    )
                }
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
        onNavigateOnboarding = {}
    )

}