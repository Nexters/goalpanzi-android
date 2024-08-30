package com.goalpanzi.mission_mate.core.data.mapper

import com.goalpanzi.mission_mate.core.domain.model.mission.BoardReward
import com.goalpanzi.mission_mate.core.domain.model.mission.CreateMissionBody
import com.goalpanzi.mission_mate.core.domain.model.mission.Mission
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionBoard
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionBoardMembers
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionBoards
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionDetail
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionRank
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionVerification
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionVerifications
import com.goalpanzi.mission_mate.core.domain.model.mission.Missions
import com.goalpanzi.mission_mate.core.network.model.request.CreateMissionRequest
import com.goalpanzi.mission_mate.core.network.model.response.BoardRewardResponse
import com.goalpanzi.mission_mate.core.network.model.response.MissionBoardMembersResponse
import com.goalpanzi.mission_mate.core.network.model.response.MissionBoardResponse
import com.goalpanzi.mission_mate.core.network.model.response.MissionBoardsResponse
import com.goalpanzi.mission_mate.core.network.model.response.MissionDetailResponse
import com.goalpanzi.mission_mate.core.network.model.response.MissionRankResponse
import com.goalpanzi.mission_mate.core.network.model.response.MissionResponse
import com.goalpanzi.mission_mate.core.network.model.response.MissionVerificationResponse
import com.goalpanzi.mission_mate.core.network.model.response.MissionVerificationsResponse
import com.goalpanzi.mission_mate.core.network.model.response.MissionsResponse
import java.time.DayOfWeek


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

fun MissionBoardsResponse.toModel() : MissionBoards {
    return MissionBoards(
        missionBoards = missionBoards.map {
            it.toModel()
        },
        progressCount = progressCount,
        rank = rank
    )
}

fun MissionBoardResponse.toModel() : MissionBoard {
    return MissionBoard(
        number = number,
        reward = reward.toModel(),
        isMyPosition = isMyPosition,
        missionBoardMembers = missionBoardMembers.map {
            it.toModel()
        }
    )
}

fun MissionBoardMembersResponse.toModel() : MissionBoardMembers {
    return MissionBoardMembers(
        nickname = nickname,
        characterType = characterType.toModel()
    )
}

fun BoardRewardResponse.toModel() : BoardReward {
    return try{
        BoardReward.valueOf(this.name)
    }catch (e : Exception){
        BoardReward.NONE
    }
}

fun MissionVerificationsResponse.toModel() : MissionVerifications {
    return MissionVerifications(
        missionVerifications = missionVerifications.map {
            it.toModel()
        }
    )
}

fun MissionVerificationResponse.toModel() : MissionVerification {
    return MissionVerification(
        nickname = nickname,
        characterType = characterType.toModel(),
        imageUrl = imageUrl,
        verifiedAt = verifiedAt
    )
}

fun MissionRankResponse.toModel() : MissionRank {
    return MissionRank(
        rank = rank
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

fun MissionResponse.toModel() : Mission {
    return Mission(
        missionId = missionId,
        description = description
    )
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