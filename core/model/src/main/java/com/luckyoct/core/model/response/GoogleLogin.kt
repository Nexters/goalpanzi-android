package com.luckyoct.core.model.response

import com.luckyoct.core.model.CharacterType
import kotlinx.serialization.Serializable

@Serializable
data class GoogleLogin(
    val accessToken: String,
    val refreshToken: String,
    val nickname: String?,
    val characterType: CharacterType?,
    val isProfileSet: Boolean
)
