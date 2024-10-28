package com.goalpanzi.mission_mate.core.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class TokenReissue(
    val accessToken: String,
    val refreshToken: String
)
