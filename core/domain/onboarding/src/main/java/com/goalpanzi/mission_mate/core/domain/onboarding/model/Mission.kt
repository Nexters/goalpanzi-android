package com.goalpanzi.mission_mate.core.domain.onboarding.model

import com.goalpanzi.mission_mate.core.domain.common.model.mission.MissionStatus

data class Mission(
    val missionId : Long,
    val description : String,
    val missionStatus : MissionStatus
)
