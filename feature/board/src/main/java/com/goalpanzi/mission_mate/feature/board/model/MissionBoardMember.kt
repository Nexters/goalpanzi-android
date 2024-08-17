package com.goalpanzi.mission_mate.feature.board.model

import com.luckyoct.core.model.response.MissionBoardMembersResponse

data class MissionBoardMember(
    val nickname : String,
    val character : Character
)

fun MissionBoardMembersResponse.toModel() : MissionBoardMember {
    return MissionBoardMember(
        nickname = nickname,
        character = characterType.toCharacter()
    )
}