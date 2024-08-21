package com.goalpanzi.core.model.response

import com.goalpanzi.core.model.CharacterType
import kotlinx.serialization.Serializable

@Serializable
data class MissionVerificationResponse(
    val nickname : String,
    val characterType : CharacterType = CharacterType.RABBIT,
    val imageUrl : String = "",
    val verifiedAt : String = ""
)
