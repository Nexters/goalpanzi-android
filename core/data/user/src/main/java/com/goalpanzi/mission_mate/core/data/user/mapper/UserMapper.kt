package com.goalpanzi.mission_mate.core.data.user.mapper

import com.goalpanzi.mission_mate.core.datastore.model.UserProfileDto
import com.goalpanzi.mission_mate.core.domain.common.model.user.CharacterType
import com.goalpanzi.mission_mate.core.domain.common.model.user.UserProfile

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
