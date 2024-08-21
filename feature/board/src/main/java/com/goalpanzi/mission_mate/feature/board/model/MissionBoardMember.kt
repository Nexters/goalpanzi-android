package com.goalpanzi.mission_mate.feature.board.model

import com.goalpanzi.core.model.response.MissionBoardMembersResponse

data class MissionBoardMember(
    val nickname : String,
    val character : Character,
 //   val memberId : Long
)

fun MissionBoardMembersResponse.toModel() : MissionBoardMember {
    return MissionBoardMember(
        nickname = nickname,
        character = characterType.toCharacter(),
       // memberId = memberId
    )
}