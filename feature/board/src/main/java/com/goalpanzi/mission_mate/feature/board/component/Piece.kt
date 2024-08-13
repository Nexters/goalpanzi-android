package com.goalpanzi.mission_mate.feature.board.component

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorOrange_FFFF5732
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.feature.board.model.PieceOffset
import com.goalpanzi.mission_mate.feature.board.util.PieceUtil
import com.goalpanzi.mission_mate.feature.onboarding.component.StableImage

@Composable
fun BoxScope.Piece(
    index: Int,
    numberOfColumn: Int,
    sizePerBlock: Dp,
    @DrawableRes imageId: Int,
    modifier: Modifier = Modifier
) {
    var needMoving by remember {
        mutableStateOf(false)
    }
    val x = animateDpAsState(
        targetValue = if (needMoving) PieceUtil.getXOffset(
            index,
            numberOfColumn,
            sizePerBlock
        ) else PieceUtil.getXOffset(index-1, numberOfColumn, sizePerBlock)
    )
    val y = animateDpAsState(
        targetValue = if (needMoving) PieceUtil.getYOffset(
            index ,
            numberOfColumn,
            sizePerBlock
        ) else PieceUtil.getYOffset(index-1, numberOfColumn, sizePerBlock)
    )


    LaunchedEffect(key1 = index) {
        needMoving = true
    }

    Box(
        modifier = modifier
            .absoluteOffset(
                x = x.value,
                y = y.value
            )
            .size(
                sizePerBlock
            )
    ) {
        StableImage(
            modifier = Modifier
                .fillMaxWidth(88f / 114f)
                .aspectRatio(1f)
                .align(Alignment.TopCenter),
            drawableResId = imageId,
        )
        PieceNameChip(
            modifier = Modifier.align(
                Alignment. BottomCenter
            ).padding(bottom = 7.dp),
            name = "토끼는깡총깡"
        )
    }


}

@Composable
fun PieceNameChip(
    name : String,
    modifier: Modifier = Modifier,
    isMe : Boolean = false,
    textStyle : TextStyle = MissionMateTypography.body_md_bold
){
    Text(
        modifier = modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(20.dp))

            .background(
                if(isMe) ColorOrange_FFFF5732 else ColorWhite_FFFFFFFF
            ).padding(horizontal = 8.5.dp, 0.85.dp)
        ,
        text = name,
        style = textStyle,
        color = if(isMe) ColorWhite_FFFFFFFF else ColorGray1_FF404249
    )
}