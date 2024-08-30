package com.goalpanzi.mission_mate.core.domain.model.mission

data class MissionBoard(
    val number : Int,
    val reward : BoardReward = BoardReward.NONE,
    val isMyPosition : Boolean = false,
    val missionBoardMembers : List<MissionBoardMembers>
)