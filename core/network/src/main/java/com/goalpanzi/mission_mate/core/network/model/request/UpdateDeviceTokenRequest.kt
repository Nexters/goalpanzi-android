package com.goalpanzi.mission_mate.core.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class UpdateDeviceTokenRequest(
    val deviceToken: String,
    val deviceIdentifier: String,
    val osType: String
)
