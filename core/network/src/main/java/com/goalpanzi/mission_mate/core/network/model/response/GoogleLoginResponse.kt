package com.goalpanzi.mission_mate.core.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class GoogleLoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val nickname: String?,
    val characterType: CharacterTypeResponse?,
    val isProfileSet: Boolean,
    val memberId: Long
)
