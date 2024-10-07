package com.goalpanzi.mission_mate.feature.board.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.feature.board.R
import com.goalpanzi.mission_mate.feature.board.model.BlockEventType
import com.goalpanzi.mission_mate.feature.board.model.BlockType
import com.goalpanzi.mission_mate.core.designsystem.component.StableImage

@Composable
fun Block(
    index: Int,
    type: BlockType,
    eventType: BlockEventType?,
    numberOfColumns: Int,
    onClickPassedBlock: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isPassed: Boolean = false,
    isStartedMission: Boolean = false
) {
    val isBright = (index % (numberOfColumns * 2)) % 2 != 0
    Box(
        modifier = modifier
    ) {
        if (type == BlockType.EMPTY) {
            Spacer(modifier = modifier)
        } else {
            BlockImage(
                index = index,
                type = type,
                eventType = eventType,
                isBright = isBright,
                onClickPassedBlock = onClickPassedBlock,
                modifier = modifier,
                isPassed = isPassed,
                isStartedMission = isStartedMission
            )
        }
        if (type == BlockType.START) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = BlockType.START.name,
                color = ColorWhite_FFFFFFFF,
                style = MissionMateTypography.title_lg_bold
            )
        } else if (
            (eventType is BlockEventType.GoalWithEvent || eventType is BlockEventType.Goal)
            && !isPassed
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "GOAL",
                color = ColorGray1_FF404249,
                style = MissionMateTypography.title_lg_bold
            )
        } else if (eventType is BlockEventType.Item && type != BlockType.EMPTY) {
            if (!isPassed) {
                StableImage(
                    modifier = Modifier
                        .fillMaxWidth(50f / 114f)
                        .fillMaxHeight(48f / 114f)
                        .align(Alignment.Center)
                        .alpha(
                            if (isStartedMission) 1f else 0.5f
                        ),
                    drawableResId = R.drawable.img_present
                )
            }
        }
    }
}

@Composable
fun BlockImage(
    index : Int,
    type: BlockType,
    eventType: BlockEventType?,
    isBright : Boolean,
    onClickPassedBlock: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isPassed: Boolean = false,
    isStartedMission: Boolean = false
) {
    val drawableRes = if (type == BlockType.START) R.drawable.img_board_start
    else if (isStartedMission && isPassed) {
        if (eventType is BlockEventType.Item) {
            eventType.boardEventItem.eventType?.imageId
        } else if (eventType is BlockEventType.GoalWithEvent) {
            eventType.boardEventItem.eventType?.imageId
        } else {
            when (type) {
                BlockType.CENTER -> R.drawable.img_board_center_jeju
                BlockType.TOP_LEFT_CORNER -> R.drawable.img_board_left_top_jeju
                BlockType.BOTTOM_LEFT_CORNER -> R.drawable.img_board_left_bottom_jeju
                BlockType.TOP_RIGHT_CORNER -> R.drawable.img_board_right_top_jeju
                BlockType.BOTTOM_RIGHT_CORNER -> R.drawable.img_board_right_bottom_jeju
                else -> null
            }
        }

    } else {
        when (type) {
            BlockType.CENTER -> if (isBright) R.drawable.img_board_center_light else R.drawable.img_board_center_dark
            BlockType.TOP_LEFT_CORNER -> if (isBright) R.drawable.img_board_left_top_light else R.drawable.img_board_left_top_dark
            BlockType.BOTTOM_LEFT_CORNER -> if (isBright) R.drawable.img_board_left_bottom_light else R.drawable.img_board_left_bottom_dark
            BlockType.TOP_RIGHT_CORNER -> if (isBright) R.drawable.img_board_right_top_light else R.drawable.img_board_right_top_dark
            BlockType.BOTTOM_RIGHT_CORNER -> if (isBright) R.drawable.img_board_right_bottom_light else R.drawable.img_board_right_bottom_dark
            else -> null
        }
    }

    if(drawableRes == null){
        Spacer(modifier = modifier)
    }else {
        StableImage(
            modifier = modifier.then(
                if (isStartedMission && isPassed) {
                    Modifier.clickable {
                        onClickPassedBlock(index)
                    }
                } else {
                    Modifier
                }
            ),
            drawableResId = drawableRes,
            contentScale = ContentScale.FillWidth
        )
    }

}
