package com.goalpanzi.mission_mate.feature.board.model

import com.goalpanzi.mission_mate.core.domain.mission.model.MissionVerification

enum class UserStoryType {
    COLORED,
    TRANSPARENT,
    NORMAL
}

data class UserStory(
    val nickname: String,
    val characterUiModelType: CharacterUiModel,
    val imageUrl: String,
    val isVerified: Boolean,
    val isMe: Boolean = false,
    val verifiedAt: String,
    val viewedAt: String,
    val missionVerificationId: Long
) {
    val isViewed = viewedAt.isNotEmpty()

    val userStoryType: UserStoryType = if (isVerified) {
        if (isViewed) {
            UserStoryType.NORMAL
        } else {
            UserStoryType.COLORED
        }
    } else {
        UserStoryType.TRANSPARENT
    }

    val userStoryAlpha = when (userStoryType) {
        UserStoryType.TRANSPARENT -> 0.4f
        else -> 1f
    }


}

fun MissionVerification.toUserStory(isMe: Boolean = false): UserStory =
    UserStory(
        nickname = nickname,
        characterUiModelType = characterType.toCharacterUiModel(),
        imageUrl = imageUrl,
        isVerified = imageUrl.isNotEmpty(),
        isMe = isMe,
        verifiedAt = verifiedAt,
        viewedAt = viewedAt,
        missionVerificationId = missionVerificationId
    )
