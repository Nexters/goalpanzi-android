package com.goalpanzi.mission_mate.feature.board.component

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray2_FF4F505C
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.core.domain.mission.model.MissionDetail
import com.goalpanzi.mission_mate.core.domain.mission.model.MissionVerification
import com.goalpanzi.mission_mate.feature.board.R
import com.goalpanzi.mission_mate.feature.board.model.BoardEventItem
import com.goalpanzi.mission_mate.feature.board.model.BoardPiece
import com.goalpanzi.mission_mate.feature.board.model.MissionBoardsUiModel
import com.goalpanzi.mission_mate.feature.board.model.MissionState
import com.goalpanzi.mission_mate.feature.board.model.toEventType
import com.goalpanzi.mission_mate.feature.board.util.BoardManager
import com.goalpanzi.mission_mate.feature.board.util.BoardManager.getPositionScrollToMyIndex
import kotlin.math.min

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun Board(
    scrollState: ScrollState,
    pullRefreshState: PullRefreshState,
    isRefreshLoading : Boolean,
    missionBoards: MissionBoardsUiModel,
    missionDetail: MissionDetail,
    numberOfColumns: Int,
    boardPieces: List<BoardPiece>,
    profile: MissionVerification,
    missionState: MissionState,
    onClickPassedBlock: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val statusBarPaddingValue = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val navigationPaddingValue =
        WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val localDensity = LocalDensity.current
    val configuration = LocalConfiguration.current
    val statusBarHeight =
        remember(statusBarPaddingValue) { statusBarPaddingValue }
    val bottomViewHeight = remember(navigationPaddingValue) {
        142.dp + navigationPaddingValue
    }
    val navigationBarHeight =
        remember(navigationPaddingValue) {
            navigationPaddingValue
        }
    val isVisiblePieces by remember(missionState) { derivedStateOf { missionState.isVisiblePiece() } }
    val myIndex by remember(missionBoards) {
        derivedStateOf {
            missionBoards.missionBoardList.find {
                it.isMyPosition
            }?.number ?: 0
        }
    }
    val refreshingSpacerSize by animateDpAsState(
        targetValue = (min(pullRefreshState.progress, 1f) * 140).dp
    )

    LaunchedEffect(myIndex) {
        scrollState.animateScrollTo(
            getPositionScrollToMyIndex(
                myIndex = myIndex,
                numberOfColumns = numberOfColumns,
                blockSize = (configuration.screenWidthDp - 48) / numberOfColumns,
                localDensity = localDensity
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(
                pullRefreshState
            )
    ) {

        Column(
            modifier = modifier.modifierWithClipRect(
                scrollState = scrollState,
                isVisiblePieces = isVisiblePieces,
                innerModifier = Modifier
                    .drawWithContent {
                        clipRect(bottom = statusBarHeight.toPx() + 178.dp.toPx()) {
                            this@drawWithContent.drawContent()
                        }
                    }
                    .blur(10.dp, 10.dp),
            )
        ) {
            BoardContent(
                missionBoards,
                missionDetail,
                numberOfColumns,
                boardPieces,
                profile,
                missionState,
                isVisiblePieces = isVisiblePieces,
                onClickPassedBlock = onClickPassedBlock,
                modifier
            )
        }
        Column(
            modifier = modifier.modifierWithClipRect(
                scrollState = scrollState,
                isVisiblePieces = isVisiblePieces,
                innerModifier = Modifier
                    .drawWithContent {
                        clipRect(
                            top = statusBarHeight.toPx() + 178.dp.toPx() - 1,
                            bottom = if (!isVisiblePieces) {
                                size.height
                            } else {
                                size.height + navigationBarHeight.toPx() - bottomViewHeight.toPx() + 1.dp.toPx()
                            }
                        ) {
                            this@drawWithContent.drawContent()
                        }
                    }

            )
        ) {
            Spacer(modifier = Modifier.height(refreshingSpacerSize))
            BoardContent(
                missionBoards,
                missionDetail,
                numberOfColumns,
                boardPieces,
                profile,
                missionState,
                isVisiblePieces = isVisiblePieces,
                onClickPassedBlock = onClickPassedBlock,
                modifier
            )
        }
        if (isVisiblePieces) {
            Column(
                modifier = modifier.modifierWithClipRect(
                    scrollState = scrollState,
                    isVisiblePieces = isVisiblePieces,
                    innerModifier = Modifier
                        .drawWithContent {
                            clipRect(top = (size.height + navigationBarHeight.toPx() - bottomViewHeight.toPx() + 1.dp.toPx())) {
                                this@drawWithContent.drawContent()
                            }
                        }
                        .blur(10.dp, 10.dp)
                )
            ) {
                Spacer(modifier = Modifier.height(refreshingSpacerSize))
                BoardContent(
                    missionBoards,
                    missionDetail,
                    numberOfColumns,
                    boardPieces,
                    profile,
                    missionState,
                    isVisiblePieces = isVisiblePieces,
                    onClickPassedBlock = onClickPassedBlock,
                    modifier
                )
            }
        }
        PullRefreshIndicator(
            refreshing = isRefreshLoading,
            state = pullRefreshState,
            modifier = Modifier.align(TopCenter).statusBarsPadding().padding(top = 178.dp)
        )
    }
}


@Composable
fun ColumnScope.BoardContent(
    missionBoards: MissionBoardsUiModel,
    missionDetail: MissionDetail,
    numberOfColumns: Int,
    boardPieces: List<BoardPiece>,
    profile: MissionVerification,
    missionState: MissionState,
    isVisiblePieces: Boolean,
    onClickPassedBlock: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val boardCount = missionBoards.missionBoardList.size
    val passedCount = missionBoards.passedCountByMe
    val startDateText = stringResource(
        id = R.string.board_before_start_title,
        missionDetail.missionStartLocalDate.monthValue,
        missionDetail.missionStartLocalDate.dayOfMonth
    )
    Text(
        modifier = Modifier.padding(top = 28.dp),
        text = if (missionState.isRankBoardTitle()) stringResource(
            id = R.string.board_rank_title,
            missionBoards.progressCount, 1
        )
        else if (missionState.isEncourageBoardTitle()) stringResource(id = R.string.board_encourage_title)
        else startDateText,
        style = MissionMateTypography.heading_md_bold,
        color = ColorGray1_FF404249
    )
    Text(
        modifier = Modifier.padding(top = 2.dp, bottom = 20.dp),
        text = if (missionState.isRankBoardTitle() || missionState.isEncourageBoardTitle()) stringResource(
            id = R.string.board_after_start_description,
            missionBoards.rank
        ) else stringResource(id = R.string.board_before_start_description),
        style = MissionMateTypography.body_lg_bold,
        color = ColorGray2_FF4F505C
    )
    BoxWithConstraints {
        val width = maxWidth
        Column {
            BoardManager.getBlockListByBoardCount(
                boardCount,
                numberOfColumns,
                passedCount,
                missionBoards.boardRewardList.map {
                    BoardEventItem(
                        index = it.number,
                        eventType = it.reward.toEventType()
                    )
                }
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
                            onClickPassedBlock = onClickPassedBlock,
                            isPassed = it.isPassed,
                            isStartedMission = isVisiblePieces
                        )
                    }
                }
            }
        }
        if (isVisiblePieces) {
            boardPieces.forEach { piece ->
                key(piece.nickname) {
                    Piece(
                        boardPiece = piece,
                        sizePerBlock = width / numberOfColumns,
                        numberOfColumn = numberOfColumns,
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.modifierWithClipRect(
    scrollState: ScrollState,
    isVisiblePieces: Boolean,
    innerModifier: Modifier,
    modifier: Modifier = Modifier,
): Modifier {
    return modifier
        .fillMaxSize()
        .navigationBarsPadding()
        .then(innerModifier)
        .verticalScroll(
            state = scrollState
        )
        .statusBarsPadding()
        .padding(
            top = 180.dp,
            start = 24.dp,
            end = 24.dp,
            bottom = if (isVisiblePieces) 188.dp else 46.dp
        )
}
