package com.goalpanzi.mission_mate.feature.board.util

import androidx.compose.ui.unit.Density
import com.goalpanzi.mission_mate.feature.board.model.BlockEventType
import com.goalpanzi.mission_mate.feature.board.model.BlockType
import com.goalpanzi.mission_mate.feature.board.model.uimodel.BlockUiModel
import com.goalpanzi.mission_mate.feature.board.model.BoardEventItem

object BoardManager {


    fun getBlockListByBoardCount(
        boardCount: Int,
        numberOfColumns: Int,
        passedCount: Int,
        eventList: List<BoardEventItem>
    ): List<BlockUiModel> {

        val quotient = boardCount / (numberOfColumns * 2)
        val remainder = boardCount % (numberOfColumns * 2)

        val blockList = mutableListOf<BlockUiModel>()

        repeat(quotient) { innerQuotient ->
            getTopRow(
                blockList,
                innerQuotient,
                numberOfColumns,
                numberOfColumns,
                boardCount,
                passedCount,
                eventList
            )
            getBottomRow(
                blockList,
                innerQuotient, numberOfColumns, numberOfColumns * 2, boardCount, passedCount,
                eventList
            )
        }
        if (remainder != 0) {
            if (remainder in 1..numberOfColumns) {
                getTopRow(
                    blockList, quotient, numberOfColumns, remainder, boardCount, passedCount,
                    eventList
                )
            } else {
                getTopRow(
                    blockList, quotient, numberOfColumns, remainder, boardCount, passedCount,
                    eventList
                )
                getBottomRow(
                    blockList, quotient, numberOfColumns, remainder, boardCount, passedCount,
                    eventList
                )
            }
        }
        return blockList
    }

    private fun getTopRow(
        blockList: MutableList<BlockUiModel>,
        quotient: Int,
        numberOfColumns: Int,
        remainder: Int,
        boardCount: Int,
        passedCount: Int,
        indicesForPresent: List<BoardEventItem>
    ) {
        for (i in 1..numberOfColumns) {
            val index = quotient * numberOfColumns * 2 + i - 1
            val itemEvent = indicesForPresent.find { it.index == index }

            blockList.add(
                BlockUiModel(
                    index = index,
                    blockType =
                    if (quotient * numberOfColumns * 2 + i - 1 == 0) BlockType.START
                    else if (i == 1) BlockType.BOTTOM_LEFT_CORNER
                    else if (index == boardCount - 1) BlockType.CENTER
                    else if (i > remainder) BlockType.EMPTY
                    else if (i == numberOfColumns) BlockType.TOP_RIGHT_CORNER
                    else BlockType.CENTER,
                    blockEventType =
                    if (itemEvent != null && index == boardCount - 1) BlockEventType.GoalWithEvent(itemEvent)
                    else if(index == boardCount - 1) BlockEventType.Goal
                    else if (itemEvent != null) BlockEventType.Item(itemEvent)
                    else BlockEventType.None,
                    isEvenGroup = quotient % 2 == 0,
                    isPassed = index <= passedCount
                )
            )
        }
    }

    private fun getBottomRow(
        blockList: MutableList<BlockUiModel>,
        quotient: Int,
        numberOfColumns: Int,
        remainder: Int,
        boardCount: Int,
        passedCount: Int,
        indicesForPresent: List<BoardEventItem>
    ) {
        for (i in numberOfColumns * 2 downTo numberOfColumns + 1) {
            val index = quotient * (numberOfColumns * 2) + i - 1
            val itemEvent = indicesForPresent.find { it.index == index }
            blockList.add(
                BlockUiModel(
                    index = index,
                    blockType =
                    if (i > remainder && remainder != 0) BlockType.EMPTY
                    else if (i == numberOfColumns + 1) BlockType.BOTTOM_RIGHT_CORNER
                    else if (index == boardCount - 1) BlockType.CENTER
                    else if (i == numberOfColumns * 2) BlockType.TOP_LEFT_CORNER
                    else BlockType.CENTER,
                    isEvenGroup = quotient % 2 == 0,
                    blockEventType =
                    if (itemEvent != null && index == boardCount) BlockEventType.GoalWithEvent(itemEvent)
                    else if(index == boardCount - 1) BlockEventType.Goal
                    else if (itemEvent != null) BlockEventType.Item(itemEvent)
                    else BlockEventType.None,
                    isPassed = index <= passedCount
                )
            )
        }
    }

    fun getPositionScrollToMyIndex(
        myIndex : Int,
        numberOfColumns: Int,
        blockSize : Int,
        localDensity: Density
    ) : Int {
        return ((myIndex / numberOfColumns) * (blockSize)  * localDensity.density).toInt()
    }

}