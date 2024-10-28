package com.goalpanzi.mission_mate.feature.board.model

import com.goalpanzi.mission_mate.core.domain.mission.model.MissionVerification


data class UserStory(
    val nickname : String,
    val characterUiModelType : CharacterUiModel,
    val imageUrl : String,
    val isVerified : Boolean,
    val isMe : Boolean = false,
    val verifiedAt : String
)

fun MissionVerification.toUserStory(isMe: Boolean = false) : UserStory =
    UserStory(
        nickname = nickname,
        characterUiModelType = characterType.toCharacterUiModel(),
        imageUrl = imageUrl,
        isVerified = imageUrl.isNotEmpty(),
        isMe = isMe,
        verifiedAt = verifiedAt
    )
