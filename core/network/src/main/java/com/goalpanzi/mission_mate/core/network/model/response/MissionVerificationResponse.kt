package com.goalpanzi.mission_mate.core.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MissionVerificationResponse(
    val nickname : String,
    val characterType : CharacterTypeResponse =CharacterTypeResponse.RABBIT,
    val imageUrl : String = "",
    val verifiedAt : String = ""
)
