package com.goalpanzi.mission_mate.feature.board.model

import com.luckyoct.core.model.response.MissionVerificationResponse

data class UserStory(
    val nickname : String,
    val characterType : Character,
    val imageUrl : String,
    val isVerified : Boolean,
    val isMe : Boolean = false
)

fun MissionVerificationResponse.toUserStory(isMe: Boolean = false) : UserStory =
    UserStory(
        nickname = nickname,
        characterType = characterType.toCharacter(),
        imageUrl = image,
        isVerified = false,
        isMe = isMe
    )