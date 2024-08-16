package com.luckyoct.core.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MissionBoardsResponse(
    val missionBoards : List<MissionBoardResponse>,
    val progressCount : Int,
    val rank : Int
){
    val boardRewardList : List<MissionBoardResponse> by lazy {
        missionBoards.filter { it.reward != BoardReward.NONE }
    }

    val passedCountByMe : Int
        get() {
            return missionBoards.find { it.isMyPosition }?.number ?: 0
        }
}