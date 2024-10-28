package com.goalpanzi.mission_mate.core.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MissionBoardsResponse(
    val missionBoards : List<MissionBoardResponse>,
    val progressCount : Int,
    val rank : Int
)