package com.goalpanzi.mission_mate.feature.board.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.feature.board.R
import com.goalpanzi.mission_mate.feature.board.component.Board
import com.goalpanzi.mission_mate.feature.board.component.BoardBottomView
import com.goalpanzi.mission_mate.feature.board.component.BoardTopView
import com.goalpanzi.mission_mate.feature.board.component.dialog.BoardEventDialog
import com.goalpanzi.mission_mate.feature.board.component.dialog.DeleteMissionDialog
import com.goalpanzi.mission_mate.feature.board.model.MissionState
import com.goalpanzi.mission_mate.feature.board.model.toCharacter
import com.goalpanzi.mission_mate.feature.board.model.toUserStory
import com.goalpanzi.mission_mate.feature.board.model.uimodel.MissionBoardUiModel
import com.goalpanzi.mission_mate.feature.board.model.uimodel.MissionUiModel
import com.goalpanzi.mission_mate.feature.board.model.uimodel.MissionVerificationUiModel
import com.goalpanzi.mission_mate.feature.onboarding.component.StableImage
import com.luckyoct.core.model.response.BoardReward
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BoardRoute(
    onNavigateOnboarding: () -> Unit,
    onClickSetting: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BoardViewModel = hiltViewModel()
) {
    val missionBoardUiModel by viewModel.missionBoardUiModel.collectAsStateWithLifecycle()
    val missionUiModel by viewModel.missionUiModel.collectAsStateWithLifecycle()
    val missionVerificationUiModel by viewModel.missionVerificationUiModel.collectAsStateWithLifecycle()
    val missionState by viewModel.missionState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    var isShownDeleteMissionDialog by remember { mutableStateOf(false) }
    var isShownBoardRewardDialog by remember { mutableStateOf<BoardReward?>(null) }

    LaunchedEffect(key1 = Unit) {
        viewModel.getMissionBoards()
        viewModel.getMission()
        viewModel.getMissionVerification()

        launch {
            viewModel.deleteMissionResultEvent.collect {
                if (it) {
                    isShownDeleteMissionDialog = false
                    onNavigateOnboarding()
                }
            }
        }

        launch {
            viewModel.boardRewardEvent.collect {
                delay(500L)
                isShownBoardRewardDialog = it
            }
        }
    }

    LaunchedEffect(missionState) {
        if (missionState == MissionState.DELETABLE) {
            isShownDeleteMissionDialog = true
        }
    }

    if (isShownDeleteMissionDialog) {
        DeleteMissionDialog(
            onDismissRequest = {
                isShownDeleteMissionDialog = false
            },
            onClickOk = {
                viewModel.deleteMission()
            }
        )
    }
    if(isShownBoardRewardDialog != null){
        BoardEventDialog(
            reward = isShownBoardRewardDialog!!,
            onDismissRequest = {
                isShownBoardRewardDialog = null
            },
            onClickOk = {
                isShownBoardRewardDialog = null
            }
        )
    }

    BoardScreen(
        modifier = modifier,
        scrollState = scrollState,
        missionBoardUiModel = missionBoardUiModel,
        missionUiModel = missionUiModel,
        missionVerificationUiModel = missionVerificationUiModel,
        missionState = missionState,
        onClickSetting = onClickSetting,
        onClickVerification = {
            viewModel.verify()
        }
    )
}

@Composable
fun BoardScreen(
    scrollState: ScrollState,
    missionBoardUiModel: MissionBoardUiModel,
    missionUiModel: MissionUiModel,
    missionVerificationUiModel: MissionVerificationUiModel,
    missionState : MissionState,
    onClickSetting: () -> Unit,
    onClickVerification : () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ColorWhite_FFFFFFFF)
    ) {
        if (missionBoardUiModel is MissionBoardUiModel.Success
            && missionUiModel is MissionUiModel.Success
            && missionVerificationUiModel is MissionVerificationUiModel.Success
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_jeju_full),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Board(
                missionBoards = missionBoardUiModel.missionBoards,
                missionDetail = missionUiModel.missionDetail,
                numberOfColumns = 3,
                scrollState = scrollState,
                profile = missionVerificationUiModel.missionVerificationsResponse.missionVerifications.first(),
                missionState = missionState
            )
            BoardTopView(
                title = missionUiModel.missionDetail.description,
                isAddingUserEnabled = true,
                userList = missionVerificationUiModel.missionVerificationsResponse.missionVerifications.mapIndexed { i, item ->
                    item.toUserStory(
                        isMe = i == 0
                    )
                },
                onClickFlag = {},
                onClickAddUser = {},
                onClickSetting = onClickSetting,
            )

            if (!missionState.enabledVerification()) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(
                            top = 180.dp,
                            bottom = if (missionUiModel.missionDetail.isStartedMission()
                                && missionVerificationUiModel.missionVerificationsResponse.missionVerifications.size != 1
                            ) 188.dp else 46.dp
                        )
                        .align(Alignment.Center),
                    contentAlignment = Alignment.TopCenter,
                ) {
                    StableImage(
                        drawableResId = R.drawable.img_tooltip_mission_delete_warning,
                        modifier = Modifier
                            .fillMaxWidth(276f / 390f)
                            .wrapContentHeight(),
                        contentScale = ContentScale.FillWidth
                    )
                    StableImage(
                        missionVerificationUiModel.missionVerificationsResponse.missionVerifications.first().characterType.toCharacter().imageId,
                        modifier = Modifier
                            .padding(top = 85.dp)
                            .fillMaxWidth(240f / 390f)
                            .aspectRatio(1f)
                    )
                }

            } else {
                BoardBottomView(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    missionState = missionState,
                    missionDetail = missionUiModel.missionDetail,
                    onClickButton = onClickVerification
                )
            }
        } else {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
