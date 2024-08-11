package com.goalpanzi.mission_mate.feature.onboarding.model

import com.luckyoct.core.model.response.MissionDetailResponse

data class MissionUiModel(
    val missionTitle : String,
    val missionPeriod : String,
    val missionDays : List<String>,
    val missionTime : VerificationTimeType,
    val missionBoardCount : Int
)

fun MissionDetailResponse.toMissionUiModel() =
    MissionUiModel(
        missionTitle = description,
        missionPeriod = missionPeriod,
        missionDays = missionDaysOfWeek,
        missionTime = VerificationTimeType.valueOf(timeOfDay),
        missionBoardCount = boardCount
    )