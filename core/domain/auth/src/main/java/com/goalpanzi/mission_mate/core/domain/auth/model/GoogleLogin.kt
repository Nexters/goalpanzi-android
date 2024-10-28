package com.goalpanzi.mission_mate.core.domain.auth.model

import com.goalpanzi.mission_mate.core.domain.common.model.user.CharacterType

data class GoogleLogin(
    val accessToken: String,
    val refreshToken: String,
    val nickname: String?,
    val characterType: CharacterType,
    val isProfileSet: Boolean,
    val memberId: Long
)
