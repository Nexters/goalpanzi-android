package com.goalpanzi.mission_mate.core.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MissionsResponse(
    val profile : ProfileResponse,
    val missions : List<MissionResponse>
)
