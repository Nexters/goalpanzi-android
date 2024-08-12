package com.luckyoct.core.model.response

import com.luckyoct.core.model.request.CharacterType
import kotlinx.serialization.Serializable

@Serializable
data class MissionBoardMembersResponse(
    val nickname : String,
    val characterType : CharacterType = CharacterType.RABBIT
)
