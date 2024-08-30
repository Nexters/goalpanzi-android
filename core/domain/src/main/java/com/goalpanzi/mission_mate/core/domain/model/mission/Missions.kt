package com.goalpanzi.mission_mate.core.domain.model.mission

import com.goalpanzi.mission_mate.core.domain.model.UserProfile

data class Missions(
    val profile : UserProfile,
    val missions : List<Mission>
)