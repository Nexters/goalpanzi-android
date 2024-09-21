package com.goalpanzi.mission_mate.core.data.user.mapper

import com.goalpanzi.mission_mate.core.datastore.model.UserProfileDto
import com.goalpanzi.mission_mate.core.domain.model.CharacterType
import com.goalpanzi.mission_mate.core.domain.model.UserProfile

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
