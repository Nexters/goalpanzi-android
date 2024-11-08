package com.goalpanzi.mission_mate.core.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class CompleteMissionRequest(
    val missionId : Long
)
