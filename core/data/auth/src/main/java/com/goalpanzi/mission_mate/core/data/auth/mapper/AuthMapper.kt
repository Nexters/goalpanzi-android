package com.goalpanzi.mission_mate.core.data.auth.mapper

import com.goalpanzi.mission_mate.core.data.common.mapper.toModel
import com.goalpanzi.mission_mate.core.domain.auth.model.GoogleLogin
import com.goalpanzi.mission_mate.core.domain.common.model.user.CharacterType
import com.goalpanzi.mission_mate.core.network.model.response.GoogleLoginResponse

fun GoogleLoginResponse.toModel() : GoogleLogin {
    return GoogleLogin(
        accessToken = accessToken,
        refreshToken = refreshToken,
        nickname = nickname,
        characterType = characterType?.toModel() ?: CharacterType.RABBIT,
        isProfileSet = isProfileSet,
        memberId = memberId
    )
}
