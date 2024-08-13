package com.goalpanzi.mission_mate.feature.board.util

import androidx.compose.ui.unit.Dp

object PieceUtil {

    fun getXOffset(
        index : Int,
        numberOfColumn : Int,
        sizePerBlock : Dp,
    ) : Dp {
        val quotient = index / numberOfColumn
        val remainder = index % numberOfColumn

        val offsetUnit = sizePerBlock.value / 2f
        val xIndex = if(quotient % 2 == 0) remainder else (numberOfColumn - remainder - 1)

        return sizePerBlock * xIndex// + offsetUnit.dp
    }

    fun getYOffset(
        index : Int,
        numberOfColumn : Int,
        sizePerBlock : Dp,
    ) : Dp {
        val quotient = index / numberOfColumn
        val offsetUnit = sizePerBlock.value / 2f

        return sizePerBlock * quotient// + offsetUnit.dp
    }
}