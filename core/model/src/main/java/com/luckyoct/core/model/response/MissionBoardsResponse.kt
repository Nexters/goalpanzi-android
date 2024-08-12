package com.luckyoct.core.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MissionBoardsResponse(
    val missionBoards : List<MissionBoardResponse>
)