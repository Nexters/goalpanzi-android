package com.goalpanzi.mission_mate.feature.board.util

import com.goalpanzi.mission_mate.feature.board.model.BlockEventType
import com.goalpanzi.mission_mate.feature.board.model.BlockType
import com.goalpanzi.mission_mate.feature.board.model.BlockUiModel
import com.goalpanzi.mission_mate.feature.board.model.BoardEventItem
import com.goalpanzi.mission_mate.feature.board.model.EventType

object BoardUtil {

    val indicesForPresent =
        listOf(
            BoardEventItem(1, EventType.Orange),
            BoardEventItem(3, EventType.Flower),
            BoardEventItem(6, EventType.Stone),
            BoardEventItem(9, EventType.Horse),
            BoardEventItem(13, EventType.Mountain),
            BoardEventItem(17, EventType.Waterfall),
            BoardEventItem(21, EventType.Pig),
            BoardEventItem(25, EventType.Bong),
            BoardEventItem(29, EventType.GreenTea),
            BoardEventItem(31, EventType.Sea)
        )

    fun getBlockListByBoardCount(
        boardCount: Int,
        numberOfColumns: Int,
        passedCount: Int
    ): List<BlockUiModel> {
        val blockCount = boardCount + 1

        val quotient = blockCount / (numberOfColumns * 2)
        val remainder = blockCount % (numberOfColumns * 2)

        val blockList = mutableListOf<BlockUiModel>()

        repeat(quotient) { innerQuotient ->
            getTopRow(
                blockList,
                innerQuotient,
                numberOfColumns,
                numberOfColumns,
                boardCount,
                passedCount
            )
            getBottomRow(
                blockList,
                innerQuotient, numberOfColumns, numberOfColumns * 2, boardCount, passedCount
            )
        }
        if (remainder != 0) {
            if (remainder in 1..numberOfColumns) {
                getTopRow(blockList, quotient, numberOfColumns, remainder, boardCount, passedCount)
            } else {
                getTopRow(blockList, quotient, numberOfColumns, remainder, boardCount, passedCount)
                getBottomRow(
                    blockList, quotient, numberOfColumns, remainder, boardCount, passedCount
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
        passedCount: Int
    ) {
        for (i in 1..numberOfColumns) {
            val index = quotient * numberOfColumns * 2 + i - 1
            val itemEvent = indicesForPresent.find { it.index == index }

            blockList.add(
                BlockUiModel(
                    index = index,
                    blockType =
                    if (quotient * numberOfColumns * 2 + i - 1 == 0) BlockType.START
                    else if (i > remainder) BlockType.EMPTY
                    else if (i == 1) BlockType.BOTTOM_LEFT_CORNER
                    else if (i == numberOfColumns) BlockType.TOP_RIGHT_CORNER
                    else BlockType.CENTER,
                    blockEventType =
                    if (index == boardCount) BlockEventType.Goal
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
        passedCount: Int
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
                    else if (i == numberOfColumns * 2) BlockType.TOP_LEFT_CORNER
                    else BlockType.CENTER,
                    isEvenGroup = quotient % 2 == 0,
                    blockEventType =
                    if (index == boardCount) BlockEventType.Goal
                    else if (itemEvent != null) BlockEventType.Item(itemEvent)
                    else BlockEventType.None,
                    isPassed = index <= passedCount
                )
            )
        }
    }
}