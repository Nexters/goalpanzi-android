package com.goalpanzi.mission_mate.core.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class VerificationInfoByBlockNumberResponse(
    val nickname: String,
    val characterType: CharacterTypeResponse,
    val missionVerificationId: Long,
    val imageUrl: String,
    val verifiedAt: String,
    val viewedAt: String
)
