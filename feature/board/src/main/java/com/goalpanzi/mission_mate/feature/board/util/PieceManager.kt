package com.goalpanzi.mission_mate.feature.board.util

import com.goalpanzi.mission_mate.feature.board.model.BoardPiece
import com.goalpanzi.mission_mate.feature.board.model.BoardPieceType
import com.goalpanzi.mission_mate.feature.board.model.MotionType

object PieceManager {
    fun getBoardPieces(
        prevBoardPieces: List<BoardPiece>,
        newBoardPieces: List<BoardPiece>
    ): List<BoardPiece> {
        val result = newBoardPieces.map { new ->
            val prevBoardPiece = prevBoardPieces.find { it.memberId == new.memberId } ?: new
            val sameIndex = prevBoardPiece.index == new.index

            val motionType = when {
                sameIndex -> when (prevBoardPiece.boardPieceType to new.boardPieceType) {
                    BoardPieceType.INITIAL to BoardPieceType.INITIAL -> MotionType.STATIC
                    BoardPieceType.INITIAL to BoardPieceType.HIDDEN -> MotionType.FADE_OUT
                    BoardPieceType.HIDDEN to BoardPieceType.INITIAL -> MotionType.FADE_IN
                    BoardPieceType.HIDDEN to BoardPieceType.HIDDEN -> MotionType.HIDE
                    else -> new.motionType
                }
                else -> when (prevBoardPiece.boardPieceType to new.boardPieceType) {
                    BoardPieceType.INITIAL to BoardPieceType.INITIAL -> MotionType.MOVE
                    BoardPieceType.INITIAL to BoardPieceType.HIDDEN -> MotionType.MOVE_FADE_OUT
                    BoardPieceType.HIDDEN to BoardPieceType.INITIAL -> MotionType.MOVE_FADE_IN
                    BoardPieceType.HIDDEN to BoardPieceType.HIDDEN -> MotionType.FADE_IN_MOVE_FADE_OUT
                    else -> new.motionType
                }
            }
            new.copy(motionType = motionType)
        }

        return result.map { item ->
            if (item.motionType != MotionType.FADE_IN_MOVE_FADE_OUT) return@map item
            val list = item.piecesWithAnimation(result)
            item.copy(
                motionType = if (list.isEmpty() || list.lastOrNull()?.memberId == item.memberId) {
                    item.motionType
                } else {
                    MotionType.HIDE
                }
            )
        }
    }

    private fun BoardPiece.piecesWithAnimation(targetList: List<BoardPiece>): List<BoardPiece> {
        return targetList.filter {
            it.index == this.index && it.motionType in setOf(
                MotionType.FADE_IN_MOVE_FADE_OUT,
                MotionType.MOVE
            )
        }
    }
}
