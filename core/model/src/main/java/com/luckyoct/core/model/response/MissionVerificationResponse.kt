package com.luckyoct.core.model.response

import com.luckyoct.core.model.CharacterType
import kotlinx.serialization.Serializable

@Serializable
data class MissionVerificationResponse(
    val nickname : String,
    val characterType : CharacterType = CharacterType.RABBIT,
    val imageUrl : String = "",
    val verifiedAt : String = ""
)
