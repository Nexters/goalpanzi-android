package com.luckyoct.core.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MissionVerificationsResponse(
    val missionVerifications : List<MissionVerificationResponse>
)