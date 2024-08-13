package com.goalpanzi.mission_mate.feature.board.model

enum class BlockType {
    TOP_RIGHT_CORNER,
    TOP_LEFT_CORNER,
    BOTTOM_RIGHT_CORNER,
    BOTTOM_LEFT_CORNER,
    CENTER,
    START,
    EMPTY
}

sealed class BlockEventType {
    data object Goal : BlockEventType()
    data class Item(val boardEventItem: BoardEventItem) : BlockEventType()
    data object None : BlockEventType()
}