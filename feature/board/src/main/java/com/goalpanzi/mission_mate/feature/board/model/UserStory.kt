package com.goalpanzi.mission_mate.feature.board.model

import com.goalpanzi.core.model.response.MissionVerificationResponse

data class UserStory(
    val nickname : String,
    val characterType : Character,
    val imageUrl : String,
    val isVerified : Boolean,
    val isMe : Boolean = false,
    val verifiedAt : String
)

fun MissionVerificationResponse.toUserStory(isMe: Boolean = false) : UserStory =
    UserStory(
        nickname = nickname,
        characterType = characterType.toCharacter(),
        imageUrl = imageUrl,
        isVerified = imageUrl.isNotEmpty(),
        isMe = isMe,
        verifiedAt = verifiedAt
    )