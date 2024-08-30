package com.goalpanzi.mission_mate.core.domain.model

data class GoogleLogin(
    val accessToken: String,
    val refreshToken: String,
    val nickname: String?,
    val characterType: CharacterType,
    val isProfileSet: Boolean,
    val memberId: Long
)
