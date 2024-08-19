package com.luckyoct.core.model.response

import com.luckyoct.core.model.CharacterType
import kotlinx.serialization.Serializable

@Serializable
data class MissionBoardMembersResponse(
    //val memberId : Long,
    val nickname : String,
    val characterType : CharacterType = CharacterType.RABBIT
)
