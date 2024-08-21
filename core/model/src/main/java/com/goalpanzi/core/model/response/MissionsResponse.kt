package com.goalpanzi.core.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MissionsResponse(
    val profile : ProfileResponse,
    val missions : List<MissionResponse>
)
