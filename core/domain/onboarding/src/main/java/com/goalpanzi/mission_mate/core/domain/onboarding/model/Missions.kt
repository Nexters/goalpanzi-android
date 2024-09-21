package com.goalpanzi.mission_mate.core.domain.onboarding.model

import com.goalpanzi.mission_mate.core.domain.common.model.user.UserProfile

data class Missions(
    val profile : UserProfile,
    val missions : List<Mission>
)
