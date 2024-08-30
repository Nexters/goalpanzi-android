package com.goalpanzi.mission_mate.core.domain.model.mission

data class MissionBoards(
    val missionBoards : List<MissionBoard>,
    val progressCount : Int,
    val rank : Int
)
