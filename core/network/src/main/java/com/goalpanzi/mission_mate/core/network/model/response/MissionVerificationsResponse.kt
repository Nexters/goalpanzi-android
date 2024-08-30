package com.goalpanzi.mission_mate.core.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MissionVerificationsResponse(
    val missionVerifications : List<MissionVerificationResponse>
)