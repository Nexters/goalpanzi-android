package com.goalpanzi.mission_mate.feature.board.model

import com.goalpanzi.core.model.response.BoardReward
import com.goalpanzi.core.model.response.MissionBoardResponse

data class MissionBoard(
    val number : Int,
    val boardReward: BoardReward,
    val isMyPosition : Boolean,
    val missionBoardMembers : List<MissionBoardMember>
)

fun MissionBoardResponse.toModel() : MissionBoard {
    return MissionBoard(
        number = number,
        isMyPosition = isMyPosition,
        boardReward = reward,
        missionBoardMembers = missionBoardMembers.map { it.toModel() }
    )
}