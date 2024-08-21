package com.goalpanzi.core.model.response

import com.goalpanzi.core.model.CharacterType
import kotlinx.serialization.Serializable

@Serializable
data class GoogleLogin(
    val accessToken: String,
    val refreshToken: String,
    val nickname: String?,
    val characterType: CharacterType?,
    val isProfileSet: Boolean,
    val memberId: Long
)
