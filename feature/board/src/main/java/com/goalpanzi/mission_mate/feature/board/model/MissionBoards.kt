package com.goalpanzi.mission_mate.feature.board.model

import com.luckyoct.core.model.response.BoardReward
import com.luckyoct.core.model.response.MissionBoardResponse
import com.luckyoct.core.model.response.MissionBoardsResponse

data class MissionBoards(
    val missionBoardList : List<MissionBoard>,
    val progressCount : Int,
    val rank : Int
){
    val boardRewardList : List<MissionBoard> by lazy {
        missionBoardList.filter { it.boardReward != BoardReward.NONE }
    }

    val passedCountByMe : Int
        get() {
            return missionBoardList.find { it.isMyPosition }?.number ?: 0
        }
}

fun MissionBoardsResponse.toModel() : MissionBoards {
    return MissionBoards(
        progressCount = progressCount,
        rank = rank,
        missionBoardList = missionBoards.map { it.toModel() }
    )
}