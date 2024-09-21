package com.goalpanzi.mission_mate.core.data.common.mapper

import com.goalpanzi.mission_mate.core.domain.common.model.user.CharacterType
import com.goalpanzi.mission_mate.core.domain.common.model.user.UserProfile
import com.goalpanzi.mission_mate.core.network.model.response.CharacterTypeResponse
import com.goalpanzi.mission_mate.core.network.model.response.ProfileResponse

fun ProfileResponse.toModel() : UserProfile {
    return UserProfile(
        nickname = nickname,
        characterType = characterType.toModel()
    )
}

fun CharacterTypeResponse.toModel() : CharacterType {
    return try {
        CharacterType.valueOf(this.name)
    }catch (e: Exception){
        CharacterType.RABBIT
    }
}

fun CharacterType.toResponse() : CharacterTypeResponse {
    return try {
        CharacterTypeResponse.valueOf(this.name)
    }catch (e: Exception){
        CharacterTypeResponse.RABBIT
    }
}

