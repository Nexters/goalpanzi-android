package com.goalpanzi.mission_mate.core.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class JoinMissionRequest(
    val invitationCode : String
)