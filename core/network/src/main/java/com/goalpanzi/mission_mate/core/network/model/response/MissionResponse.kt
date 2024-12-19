package com.goalpanzi.mission_mate.core.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MissionResponse(
    val missionId : Long,
    val description : String,
    val missionStatus : MissionStatusResponse
)
