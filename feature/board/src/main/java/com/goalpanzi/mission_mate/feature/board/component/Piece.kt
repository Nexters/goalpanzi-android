package com.goalpanzi.mission_mate.feature.board.component

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorOrange_FFFF5732
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.feature.board.model.BoardPiece
import com.goalpanzi.mission_mate.feature.board.model.BoardPieceType
import com.goalpanzi.mission_mate.feature.board.util.PieceGenerator
import com.goalpanzi.mission_mate.feature.onboarding.component.OutlinedBox
import com.goalpanzi.mission_mate.feature.onboarding.component.StableImage

@Composable
fun BoxScope.Piece(
    boardPiece: BoardPiece,
    numberOfColumn: Int,
    sizePerBlock: Dp,
    modifier: Modifier = Modifier
) {
    val isAnimated by remember(boardPiece) {
        derivedStateOf { boardPiece.boardPieceType == BoardPieceType.MOVED }
    }
    val x = animateDpAsState(
        targetValue = if (isAnimated) PieceGenerator.getXOffset(
            boardPiece.index + 1,
            numberOfColumn,
            sizePerBlock
        ) else PieceGenerator.getXOffset(boardPiece.index , numberOfColumn, sizePerBlock),
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        )
    )
    val y = animateDpAsState(
        targetValue = if (isAnimated) PieceGenerator.getYOffset(
            boardPiece.index  + 1,
            numberOfColumn,
            sizePerBlock
        ) else PieceGenerator.getYOffset(boardPiece.index , numberOfColumn, sizePerBlock),
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        )
    )


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
        if(boardPiece.boardPieceType != BoardPieceType.HIDDEN){
            StableImage(
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(88f / 114f)
                    .aspectRatio(1f)
                    .align(Alignment.TopCenter),
                drawableResId = boardPiece.drawableRes,
            )
            if(boardPiece.count > 1){
                PieceCountChip(
                    modifier = Modifier.align(Alignment.TopEnd),
                    count = boardPiece.count,
                    imageId = boardPiece.drawableRes
                )
            }
            PieceNameChip(
                modifier = Modifier
                    .align(
                        Alignment.BottomCenter
                    )
                    .padding(bottom = 7.dp),
                name = boardPiece.nickname,
                isMe = boardPiece.isMe
            )
        }

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
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (isMe) ColorOrange_FFFF5732 else ColorWhite_FFFFFFFF
            )
            .padding(horizontal = 8.5.dp, 0.85.dp)
        ,
        text = name,
        style = textStyle,
        color = if(isMe) ColorWhite_FFFFFFFF else ColorGray1_FF404249,
        textAlign = TextAlign.Center
    )
}

@Composable
fun PieceCountChip(
    @DrawableRes imageId: Int,
    count : Int,
    modifier: Modifier = Modifier,
    textStyle : TextStyle = MissionMateTypography.body_md_bold
){
    OutlinedBox(
        modifier = modifier
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ){
            StableImage(
                modifier = Modifier.size(22.dp),
                drawableResId = imageId,
            )
            Text(
                modifier = Modifier.padding(end = 4.dp),
                text = "$count",
                style = textStyle,
                color = ColorWhite_FFFFFFFF
            )
        }
    }

}