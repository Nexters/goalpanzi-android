package com.goalpanzi.mission_mate.feature.board.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray2_FF4F505C
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray5_80F5F6F9
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.feature.board.R
import com.goalpanzi.mission_mate.feature.board.component.Block
import com.goalpanzi.mission_mate.feature.board.component.BoardBottomView
import com.goalpanzi.mission_mate.feature.board.component.BoardTopView
import com.goalpanzi.mission_mate.feature.board.component.DeleteMissionDialog
import com.goalpanzi.mission_mate.feature.board.component.Piece
import com.goalpanzi.mission_mate.feature.board.model.Character
import com.goalpanzi.mission_mate.feature.board.model.MissionBoardUiModel
import com.goalpanzi.mission_mate.feature.board.model.MissionUiModel
import com.goalpanzi.mission_mate.feature.board.model.MissionVerificationUiModel
import com.goalpanzi.mission_mate.feature.board.model.toCharacter
import com.goalpanzi.mission_mate.feature.board.model.toUserStory
import com.goalpanzi.mission_mate.feature.board.util.BoardUtil
import com.goalpanzi.mission_mate.feature.onboarding.component.StableImage
import com.luckyoct.core.model.response.MissionBoardResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BoardRoute(
    onNavigateOnboarding : () -> Unit,
    onClickSetting : () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BoardViewModel = hiltViewModel()
) {
    val missionBoardUiModel by viewModel.missionBoardUiModel.collectAsStateWithLifecycle()
    val missionUiModel by viewModel.missionUiModel.collectAsStateWithLifecycle()
    val missionVerificationUiModel by viewModel.missionVerificationUiModel.collectAsStateWithLifecycle()
    var isShownDeleteMissionDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getMissionBoards()
        viewModel.getMission()
        viewModel.getMissionVerification()

        launch {
            viewModel.deleteMissionResultEvent.collect {
                if(it){
                    isShownDeleteMissionDialog = false
                    onNavigateOnboarding()
                }
            }
        }
    }

    LaunchedEffect(missionUiModel,missionVerificationUiModel) {
        if(missionUiModel is MissionUiModel.Success && missionVerificationUiModel is MissionVerificationUiModel.Success){
            if((missionUiModel as MissionUiModel.Success).missionDetailResponse.isStartedMission() &&
                (missionVerificationUiModel as MissionVerificationUiModel.Success).missionVerificationsResponse.missionVerifications.size == 1){
                isShownDeleteMissionDialog = true
            }
        }
    }

    if(isShownDeleteMissionDialog){
        DeleteMissionDialog(
            onDismissRequest = {
                isShownDeleteMissionDialog = false
            },
            onClickOk = {
                viewModel.deleteMission()
            }
        )
    }

    BoardScreen(
        missionBoardUiModel = missionBoardUiModel,
        missionUiModel = missionUiModel,
        missionVerificationUiModel = missionVerificationUiModel,
        onClickSetting = onClickSetting
    )
}

@Composable
fun BoardScreen(
    missionBoardUiModel: MissionBoardUiModel,
    missionUiModel: MissionUiModel,
    missionVerificationUiModel : MissionVerificationUiModel,
    onClickSetting : () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

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
                boardCount = missionBoardUiModel.missionBoardsResponse.missionBoards.size,
                missionBoards = missionBoardUiModel.missionBoardsResponse.missionBoards,
                numberOfColumns = 3,
                passedCount = 0,
                startDateText = stringResource(
                    id = R.string.board_before_start_title,
                    missionUiModel.missionDetailResponse.missionStartLocalDate.monthValue,
                    missionUiModel.missionDetailResponse.missionStartLocalDate.dayOfMonth
                ),
                scrollState = scrollState,
                isStartedMission = missionUiModel.missionDetailResponse.isStartedMission(),
                hasEnoughPeople = missionVerificationUiModel.missionVerificationsResponse.missionVerifications.size != 1
            )
            BoardTopView(
                title = missionUiModel.missionDetailResponse.description,
                isAddingUserEnabled = false,
                userList = missionVerificationUiModel.missionVerificationsResponse.missionVerifications.mapIndexed { i, item ->
                    item.toUserStory(
                        isMe = i == 0
                    )
                },
                onClickFlag = {},
                onClickAddUser = {},
                onClickSetting = onClickSetting,
            )


            if (!(missionUiModel.missionDetailResponse.isStartedMission()
                        && missionVerificationUiModel.missionVerificationsResponse.missionVerifications.size != 1)) {
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(
                            top = 180.dp,
                            bottom = if (missionUiModel.missionDetailResponse.isStartedMission()
                                &&  missionVerificationUiModel.missionVerificationsResponse.missionVerifications.size != 1) 188.dp else 46.dp
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
                    onClickButton = {

                    }
                )
            }
        }else {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun Board(
    scrollState: ScrollState,
    boardCount: Int,
    passedCount: Int,
    isStartedMission: Boolean,
    startDateText: String,
    missionBoards: List<MissionBoardResponse>,
    modifier: Modifier = Modifier,
    numberOfColumns: Int,
    hasEnoughPeople : Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .verticalScroll(scrollState)
            .statusBarsPadding()
            .padding(
                top = 180.dp,
                start = 24.dp,
                end = 24.dp,
                bottom = if (hasEnoughPeople && isStartedMission) 188.dp else 46.dp
            )

    ) {

        Text(
            modifier = Modifier.padding(top = 28.dp),
            text = startDateText,
            style = MissionMateTypography.heading_md_bold,
            color = ColorGray1_FF404249
        )
        Text(
            modifier = Modifier.padding(top = 2.dp, bottom = 20.dp),
            text = stringResource(id = R.string.board_before_start_description),
            style = MissionMateTypography.body_lg_bold,
            color = ColorGray2_FF4F505C
        )
        BoxWithConstraints {
            val width = maxWidth
            Column {
                BoardUtil.getBlockListByBoardCount(
                    boardCount,
                    numberOfColumns,
                    passedCount
                ).chunked(numberOfColumns).forEach {
                    Row() {
                        it.forEach {
                            Block(
                                index = it.index,
                                eventType = it.blockEventType,
                                type = it.blockType, modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f),
                                numberOfColumns = numberOfColumns,
                                isPassed = it.isPassed,
                                isStartedMission = isStartedMission
                            )
                        }
                    }
                }
            }
            if (hasEnoughPeople) {
                missionBoards.forEach { block ->
                    if (block.missionBoardMembers.isNotEmpty()) {
                        Piece(
                            index = block.number,
                            sizePerBlock = width / numberOfColumns,
                            numberOfColumn = numberOfColumns,
                            imageId = block.missionBoardMembers.first().characterType.toCharacter().imageId
                        )
                    }
                }
            }
        }
    }
}