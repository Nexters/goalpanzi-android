package com.goalpanzi.mission_mate.feature.board.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.feature.board.R
import com.goalpanzi.mission_mate.feature.board.component.Board
import com.goalpanzi.mission_mate.feature.board.component.BoardBottomView
import com.goalpanzi.mission_mate.feature.board.component.BoardTopView
import com.goalpanzi.mission_mate.feature.board.component.InvitationCodeDialog
import com.goalpanzi.mission_mate.feature.board.component.dialog.BoardEventDialog
import com.goalpanzi.mission_mate.feature.board.component.dialog.DeleteMissionDialog
import com.goalpanzi.mission_mate.feature.board.model.BoardPiece
import com.goalpanzi.mission_mate.feature.board.model.MissionState
import com.goalpanzi.mission_mate.feature.board.model.toCharacter
import com.goalpanzi.mission_mate.feature.board.model.toUserStory
import com.goalpanzi.mission_mate.feature.board.model.uimodel.MissionBoardUiModel
import com.goalpanzi.mission_mate.feature.board.model.uimodel.MissionUiModel
import com.goalpanzi.mission_mate.feature.board.model.uimodel.MissionVerificationUiModel
import com.goalpanzi.mission_mate.feature.board.util.ImageCompressor
import com.goalpanzi.mission_mate.feature.onboarding.component.StableImage
import com.luckyoct.core.model.response.BoardReward
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BoardRoute(
    onNavigateOnboarding: () -> Unit,
    onNavigateDetail: () -> Unit,
    onClickSetting: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BoardViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val missionBoardUiModel by viewModel.missionBoardUiModel.collectAsStateWithLifecycle()
    val missionUiModel by viewModel.missionUiModel.collectAsStateWithLifecycle()
    val missionVerificationUiModel by viewModel.missionVerificationUiModel.collectAsStateWithLifecycle()
    val missionState by viewModel.missionState.collectAsStateWithLifecycle()
    val viewedTooltip by viewModel.viewedToolTip.collectAsStateWithLifecycle()
    val isHost by viewModel.isHost.collectAsStateWithLifecycle()
    val boardPieces by viewModel.boardPieces.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()
    var isShownDeleteMissionDialog by remember { mutableStateOf(false) }
    var isShownBoardRewardDialog by remember { mutableStateOf<BoardReward?>(null) }
    var isShownInvitationCodeDialog by remember { mutableStateOf(false) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { imageUri ->
            imageUri?.let { original ->
                ImageCompressor.getCompressedImage(context, original).let { compressed ->
                    viewModel.verify(compressed)
                }
            }
        }
    )

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
    if (isShownBoardRewardDialog != null) {
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
    if (isShownInvitationCodeDialog) {
        if (missionUiModel !is MissionUiModel.Success) return
        InvitationCodeDialog(
            code = (missionUiModel as MissionUiModel.Success).missionDetail.invitationCode,
            onDismissRequest = {
                isShownInvitationCodeDialog = !isShownInvitationCodeDialog
            }
        )
    }

    BoardScreen(
        modifier = modifier,
        viewedTooltip = viewedTooltip,
        scrollState = scrollState,
        missionBoardUiModel = missionBoardUiModel,
        missionUiModel = missionUiModel,
        missionVerificationUiModel = missionVerificationUiModel,
        missionState = missionState,
        boardPieces = boardPieces,
        isHost = isHost,
        onClickSetting = onClickSetting,
        onClickFlag = {
            viewModel.setViewedTooltip()
            onNavigateDetail()
        },
        onClickAddUser = {
            viewModel.setViewedTooltip()
            isShownInvitationCodeDialog = !isShownInvitationCodeDialog
        },
        onClickVerification = {
            imagePicker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        },
        onClickTooltip = {
            viewModel.setViewedTooltip()
        }
    )
}

@Composable
fun BoardScreen(
    scrollState: ScrollState,
    viewedTooltip: Boolean,
    missionBoardUiModel: MissionBoardUiModel,
    missionUiModel: MissionUiModel,
    missionVerificationUiModel: MissionVerificationUiModel,
    missionState: MissionState,
    boardPieces: List<BoardPiece>,
    isHost: Boolean,
    onClickSetting: () -> Unit,
    onClickVerification: () -> Unit,
    onClickFlag: () -> Unit,
    onClickAddUser: () -> Unit,
    onClickTooltip: () -> Unit,
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
                boardPieces = boardPieces,
                scrollState = scrollState,
                profile = missionVerificationUiModel.missionVerificationsResponse.missionVerifications.first(),
                missionState = missionState
            )
            BoardTopView(
                title = missionUiModel.missionDetail.description,
                isAddingUserEnabled = isHost,
                viewedTooltip = viewedTooltip,
                userList = missionVerificationUiModel.missionVerificationsResponse.missionVerifications.mapIndexed { i, item ->
                    item.toUserStory(
                        isMe = i == 0
                    )
                },
                onClickFlag = onClickFlag,
                onClickAddUser = onClickAddUser,
                onClickSetting = onClickSetting,
                onClickTooltip = onClickTooltip
            )

            if (!missionState.isVisiblePiece()) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(
                            top = 180.dp,
                            bottom = if (missionState.isVisiblePiece()) 188.dp else 46.dp
                        )
                        .align(Alignment.Center),
                    contentAlignment = Alignment.TopCenter,
                ) {
                    if (missionState == MissionState.PRE_START_SOLO) {
                        StableImage(
                            drawableResId = R.drawable.img_tooltip_mission_delete_warning,
                            modifier = Modifier
                                .fillMaxWidth(276f / 390f)
                                .wrapContentHeight(),
                            contentScale = ContentScale.FillWidth
                        )
                    } else if (missionState == MissionState.PRE_START_MULTI) {
                        StableImage(
                            drawableResId = R.drawable.img_tooltip_mission_welcome,
                            modifier = Modifier
                                .fillMaxWidth(276f / 390f)
                                .wrapContentHeight(),
                            contentScale = ContentScale.FillWidth
                        )
                    }
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
