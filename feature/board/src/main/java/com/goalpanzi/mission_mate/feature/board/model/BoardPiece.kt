package com.goalpanzi.mission_mate.feature.board.model

data class BoardPiece(
    val memberId : Long,
    val index : Int,
    val count : Int,
    val nickname : String,
    val isMe : Boolean,
    val drawableRes: Int,
    val boardPieceType: BoardPieceType = BoardPieceType.INITIAL,
    val motionType : MotionType = MotionType.STATIC,
    val order : Int = 0
){
    val needToDraw = boardPieceType == BoardPieceType.HIDDEN && motionType in setOf(MotionType.STATIC,MotionType.HIDE,MotionType.FADE_OUT)
}

enum class BoardPieceType {
    INITIAL,
    HIDDEN
}

enum class MotionType {
    STATIC,
    MOVE,
    HIDE,
    FADE_IN,
    FADE_OUT,
    MOVE_FADE_IN,
    MOVE_FADE_OUT,
    FADE_IN_MOVE_FADE_OUT
}
