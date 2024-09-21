package com.goalpanzi.mission_mate.core.data.common.mapper

import com.goalpanzi.mission_mate.core.domain.model.CharacterType
import com.goalpanzi.mission_mate.core.domain.model.UserProfile
import com.goalpanzi.mission_mate.core.domain.model.mission.CreateMissionBody
import com.goalpanzi.mission_mate.core.domain.model.mission.Mission
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionDetail
import com.goalpanzi.mission_mate.core.domain.model.mission.Missions
import com.goalpanzi.mission_mate.core.network.model.request.CreateMissionRequest
import com.goalpanzi.mission_mate.core.network.model.response.CharacterTypeResponse
import com.goalpanzi.mission_mate.core.network.model.response.MissionDetailResponse
import com.goalpanzi.mission_mate.core.network.model.response.MissionResponse
import com.goalpanzi.mission_mate.core.network.model.response.MissionsResponse
import com.goalpanzi.mission_mate.core.network.model.response.ProfileResponse
import java.time.DayOfWeek

fun ProfileResponse.toModel() : UserProfile {
    return UserProfile(
        nickname = nickname,
        characterType = characterType.toModel()
    )
}

fun CharacterTypeResponse.toModel() : CharacterType {
    return try {
        CharacterType.valueOf(this.name)
    }catch (e: Exception){
        CharacterType.RABBIT
    }
}

fun CharacterType.toResponse() : CharacterTypeResponse {
    return try {
        CharacterTypeResponse.valueOf(this.name)
    }catch (e: Exception){
        CharacterTypeResponse.RABBIT
    }
}


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

fun MissionDetailResponse.toModel() : MissionDetail {
    return MissionDetail(
        missionId = missionId,
        hostMemberId = hostMemberId,
        description = description,
        missionStartDate = missionStartDate,
        missionEndDate = missionEndDate,
        boardCount = boardCount,
        invitationCode = invitationCode,
        missionDays =  missionDays.map {
            DayOfWeek.valueOf(it)
        },
        timeOfDay = timeOfDay
    )
}


fun MissionResponse.toModel() : Mission {
    return Mission(
        missionId = missionId,
        description = description
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
