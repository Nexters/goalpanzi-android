package com.luckyoct.core.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MissionBoardResponse(
    val number : Int,
    val reward : BoardReward = BoardReward.NONE,
    val isMyPosition : Boolean = false,
    val missionBoardMembers : List<MissionBoardMembersResponse>
)

enum class BoardReward {
    ORANGE, CANOLA_FLOWER, DOLHARUBANG, HORSE_RIDING, HALLA_MOUNTAIN, WATERFALL, BLACK_PIG, SUNRISE, GREEN_TEA_FIELD, BEACH, NONE
}