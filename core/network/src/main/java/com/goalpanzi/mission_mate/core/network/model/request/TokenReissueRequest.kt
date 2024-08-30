package com.goalpanzi.mission_mate.core.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class TokenReissueRequest(
    val refreshToken: String
)
