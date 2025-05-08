package com.goalpanzi.mission_mate.feature.board.model

data class UserStoryEntry(
    val missionId: Long,
    val characterUiModel: CharacterUiModel,
    val position: Int
)
