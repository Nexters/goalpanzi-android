package com.luckyoct.core.model.request

import kotlinx.serialization.Serializable

@Serializable
data class GoogleLoginRequest(
    val identityToken: String,
    val email: String
)
