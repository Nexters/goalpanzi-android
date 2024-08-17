package com.goalpanzi.mission_mate.feature.board.component

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray2_FF4F505C
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.feature.board.R
import com.goalpanzi.mission_mate.feature.board.model.BoardEventItem
import com.goalpanzi.mission_mate.feature.board.model.MissionBoards
import com.goalpanzi.mission_mate.feature.board.model.MissionDetail
import com.goalpanzi.mission_mate.feature.board.model.MissionState
import com.goalpanzi.mission_mate.feature.board.model.toCharacter
import com.goalpanzi.mission_mate.feature.board.model.toEventType
import com.goalpanzi.mission_mate.feature.board.util.BoardGenerator
import com.luckyoct.core.model.response.MissionBoardsResponse
import com.luckyoct.core.model.response.MissionVerificationResponse
import kotlin.math.absoluteValue


@Composable
fun Board(
    scrollState: ScrollState,
    missionBoards: MissionBoards,
    missionDetail: MissionDetail,
    numberOfColumns: Int,
    profile: MissionVerificationResponse,
    missionState: MissionState,
    modifier: Modifier = Modifier,
) {
    val statusBar = WindowInsets.statusBars
    val navigationBar = WindowInsets.navigationBars
    val localDensity = LocalDensity.current
    val statusBarHeight =
        remember { (statusBar.getTop(localDensity) - statusBar.getBottom(localDensity)).absoluteValue }
    val navigationBarHeight =
        remember { (navigationBar.getTop(localDensity) - navigationBar.getBottom(localDensity)).absoluteValue }


    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier.modifierWithClipRect(
                scrollState = scrollState,
                missionState = missionState,
                innerModifier = Modifier
                    .drawWithContent {
                        clipRect(bottom = statusBarHeight + 178.dp.toPx()) {
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
                profile,
                missionState,
                modifier
            )
        }
        Column(
            modifier = modifier.modifierWithClipRect(
                scrollState = scrollState,
                missionState = missionState,
                innerModifier = Modifier
                    .drawWithContent {
                        clipRect(
                            top = statusBarHeight + 178.dp.toPx() - 1,
                            bottom = if (missionState.isVisiblePiece()){
                                size.height
                            }else {
                                size.height + navigationBarHeight - (if (missionState.isVisiblePiece()) 188.dp else 46.dp).toPx()
                            }


                        ) {
                            this@drawWithContent.drawContent()
                        }
                    }
            )
        ) {
            BoardContent(
                missionBoards,
                missionDetail,
                numberOfColumns,
                profile,
                missionState,
                modifier
            )
        }
        if (missionState.isVisiblePiece()){
            Column(
                modifier = modifier.modifierWithClipRect(
                    scrollState = scrollState,
                    missionState = missionState,
                    innerModifier = Modifier
                        .drawWithContent {
                            clipRect(top = (size.height + navigationBarHeight - (if (missionState.isVisiblePiece()) 188.dp else 46.dp).toPx())) {
                                this@drawWithContent.drawContent()
                            }
                        }
                        .blur(10.dp, 10.dp)
                )
            ) {
                BoardContent(
                    missionBoards,
                    missionDetail,
                    numberOfColumns,
                    profile,
                    missionState,
                    modifier
                )
            }
        }
    }

}

@Composable
fun ColumnScope.BoardContent(
    missionBoards: MissionBoards,
    missionDetail: MissionDetail,
    numberOfColumns: Int,
    profile: MissionVerificationResponse,
    missionState: MissionState,
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
            missionBoards.progressCount,1
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
            BoardGenerator.getBlockListByBoardCount(
                boardCount,
                numberOfColumns,
                passedCount,
                missionBoards.boardRewardList.map {
                    BoardEventItem(
                        index = it.number,
                        eventType = it.boardReward.toEventType()
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
                            isPassed = it.isPassed,
                            isStartedMission = missionState.isVisiblePiece()
                        )
                    }
                }
            }
        }
        if (missionState.isVisiblePiece()) {
            missionBoards.missionBoardList.forEach { block ->
                if (block.missionBoardMembers.isNotEmpty()) {
                    Piece(
                        index = block.number,
                        count = block.missionBoardMembers.size,
                        nickname = if (block.isMyPosition) profile.nickname else block.missionBoardMembers.first().nickname,
                        sizePerBlock = width / numberOfColumns,
                        numberOfColumn = numberOfColumns,
                        isMe = block.isMyPosition,
                        imageId = if (block.isMyPosition) profile.characterType.toCharacter().imageId else block.missionBoardMembers.first().character.imageId,
                        imageIdForCount = if (block.isMyPosition) profile.characterType.toCharacter().imageId else block.missionBoardMembers.first().character.imageId
                    )
                }
            }
        }
    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.modifierWithClipRect(
    scrollState: ScrollState,
    missionState: MissionState,
    innerModifier: Modifier,
    modifier: Modifier = Modifier,
): Modifier {
    return modifier
        .fillMaxSize()
        .navigationBarsPadding()
        .then(innerModifier)
        .verticalScroll(scrollState)
        .statusBarsPadding()
        .padding(
            top = 180.dp,
            start = 24.dp,
            end = 24.dp,
            bottom = if (missionState.isVisiblePiece()) 188.dp else 46.dp
        )
}