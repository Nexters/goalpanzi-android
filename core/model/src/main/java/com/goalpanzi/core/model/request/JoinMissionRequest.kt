package com.goalpanzi.core.model.request

import kotlinx.serialization.Serializable

@Serializable
data class JoinMissionRequest(
    val invitationCode : String
)