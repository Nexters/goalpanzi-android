package com.goalpanzi.core.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MissionBoardsResponse(
    val missionBoards : List<MissionBoardResponse>,
    val progressCount : Int,
    val rank : Int
)