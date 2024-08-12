package com.goalpanzi.mission_mate.core.domain.repository

import com.goalpanzi.mission_mate.core.network.ResultHandler
import com.luckyoct.core.model.base.NetworkResult
import com.luckyoct.core.model.request.CreateMissionRequest
import com.luckyoct.core.model.response.MissionDetailResponse
import com.luckyoct.core.model.response.MissionsResponse

interface OnboardingRepository : ResultHandler {
    suspend fun createMission(
        missionRequest: CreateMissionRequest
    ): NetworkResult<MissionDetailResponse>

    suspend fun getMissionByInvitationCode(
        invitationCode : String
    ) : NetworkResult<MissionDetailResponse>

    suspend fun joinMission(
        invitationCode: String
    ) : NetworkResult<Unit>

    suspend fun getJoinedMissions() : NetworkResult<MissionsResponse>
}