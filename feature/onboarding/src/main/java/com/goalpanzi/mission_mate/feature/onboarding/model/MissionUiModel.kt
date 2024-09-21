package com.goalpanzi.mission_mate.feature.onboarding.model

import com.goalpanzi.mission_mate.core.domain.mission.model.MissionDetail

data class MissionUiModel(
    val missionId : Long,
    val missionTitle : String,
    val missionPeriod : String,
    val missionDays : List<String>,
    val missionTime : VerificationTimeType,
    val missionBoardCount : Int
)

fun MissionDetail.toMissionUiModel() =
    MissionUiModel(
        missionId = missionId,
        missionTitle = description,
        missionPeriod = missionPeriod,
        missionDays = missionDaysOfWeekTextLocale,
        missionTime = VerificationTimeType.valueOf(timeOfDay),
        missionBoardCount = boardCount
    )
