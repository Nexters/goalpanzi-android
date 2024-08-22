package com.goalpanzi.core.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MissionResponse(
    val missionId : Long,
    val description : String
)