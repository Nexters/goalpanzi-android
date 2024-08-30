package com.goalpanzi.mission_mate.core.data.mapper

import com.goalpanzi.mission_mate.core.datastore.model.UserProfileDto
import com.goalpanzi.mission_mate.core.domain.model.CharacterType
import com.goalpanzi.mission_mate.core.domain.model.UserProfile
import com.goalpanzi.mission_mate.core.network.model.response.CharacterTypeResponse
import com.goalpanzi.mission_mate.core.network.model.response.ProfileResponse

fun ProfileResponse.toModel() : UserProfile {
    return UserProfile(
        nickname = nickname,
        characterType = characterType.toModel()
    )
}

fun UserProfile.toDto() : UserProfileDto {
    return UserProfileDto(
        nickname = nickname,
        characterType = characterType.name.uppercase()
    )
}

fun UserProfileDto.toModel() : UserProfile {
    return UserProfile(
        nickname = nickname,
        characterType = CharacterType.valueOf(characterType)
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