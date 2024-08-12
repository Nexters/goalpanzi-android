package com.goalpanzi.mission_mate.feature.board.model

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class PieceUiModel(
    val index : Int
)

data class PieceOffset(
    val x : Dp = 0.dp,
    val y : Dp = 0.dp
)