package com.goalpanzi.mission_mate.core.domain.model.mission

data class CreateMissionBody(
    val description : String,
    val missionStartDate : String,
    val missionEndDate : String,
    val timeOfDay : String,
    val missionDays : List<String>,
    val boardCount : Int
)