package com.goalpanzi.mission_mate.core.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class GoogleLoginRequest(
    val email: String,
    val deviceIdentifier: String,
)
