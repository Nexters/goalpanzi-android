package com.luckyoct.core.model

import kotlinx.serialization.Serializable

@Serializable
data class GoogleLogin(
    val accessToken: String,
    val refreshToken: String,
    val isProfileSet: Boolean
)
