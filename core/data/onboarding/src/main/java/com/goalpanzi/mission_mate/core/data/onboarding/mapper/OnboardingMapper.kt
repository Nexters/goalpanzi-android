package com.goalpanzi.mission_mate.core.data.onboarding.mapper

import com.goalpanzi.mission_mate.core.data.common.mapper.toModel
import com.goalpanzi.mission_mate.core.domain.common.model.mission.MissionStatus
import com.goalpanzi.mission_mate.core.domain.onboarding.model.CreateMissionBody
import com.goalpanzi.mission_mate.core.domain.onboarding.model.Mission
import com.goalpanzi.mission_mate.core.domain.onboarding.model.Missions
import com.goalpanzi.mission_mate.core.network.model.request.CreateMissionRequest
import com.goalpanzi.mission_mate.core.network.model.response.MissionResponse
import com.goalpanzi.mission_mate.core.network.model.response.MissionStatusResponse
import com.goalpanzi.mission_mate.core.network.model.response.MissionsResponse

fun CreateMissionBody.toRequest() : CreateMissionRequest {
    return CreateMissionRequest(
        description = description,
        missionStartDate = missionStartDate,
        missionEndDate = missionEndDate,
        timeOfDay = timeOfDay,
        missionDays = missionDays,
        boardCount = boardCount
    )
}

fun MissionResponse.toModel() : Mission {
    return Mission(
        missionId = missionId,
        description = description,
        missionStatus = missionStatus.toModel()
    )
}


fun MissionsResponse.toModel() : Missions {
    return Missions(
        profile = profile.toModel(),
        missions = missions.map {
            it.toModel()
        }
    )
}

fun MissionStatusResponse.toModel() : MissionStatus {
    return try{
        MissionStatus.valueOf(this.name)
    }catch (e : Exception){
        MissionStatus.COMPLETED
    }
}
