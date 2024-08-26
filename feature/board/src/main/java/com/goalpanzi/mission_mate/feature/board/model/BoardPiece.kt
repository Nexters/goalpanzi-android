package com.goalpanzi.mission_mate.feature.board.model

data class BoardPiece(
    val index : Int,
    val count : Int,
    val nickname : String,
    val isMe : Boolean,
    val drawableRes: Int,
    val boardPieceType: BoardPieceType = BoardPieceType.INITIAL
)

enum class BoardPieceType {
    INITIAL,
    MOVED,
    NOT_CHANGED,
    HIDDEN
}