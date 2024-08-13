package com.goalpanzi.mission_mate.feature.board.model

data class BlockUiModel(
    val index : Int,
    val blockType : BlockType,
    val blockEventType : BlockEventType = BlockEventType.None,
    val isEvenGroup : Boolean,
    val isPassed : Boolean = false
)
