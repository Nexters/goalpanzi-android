package com.goalpanzi.mission_mate.core.domain.mission.model

data class MissionBoards(
    val missionBoards : List<MissionBoard>,
    val progressCount : Int,
    val rank : Int
)
