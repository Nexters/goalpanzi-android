package com.goalpanzi.mission_mate.core.data.repository

import com.goalpanzi.mission_mate.core.domain.repository.OnboardingRepository
import com.goalpanzi.mission_mate.core.network.service.OnboardingService
import com.luckyoct.core.model.base.NetworkResult
import com.luckyoct.core.model.request.CreateMissionRequest
import com.luckyoct.core.model.request.JoinMissionRequest
import com.luckyoct.core.model.response.MissionDetailResponse
import com.luckyoct.core.model.response.MissionsResponse
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val onboardingService: OnboardingService
) : OnboardingRepository {
    override suspend fun createMission(missionRequest: CreateMissionRequest): NetworkResult<MissionDetailResponse> = handleResult {
        onboardingService.createMission(missionRequest)
    }

    override suspend fun getMissionByInvitationCode(invitationCode: String): NetworkResult<MissionDetailResponse> = handleResult{
        onboardingService.getMissionByInvitationCode(invitationCode)
    }

    override suspend fun joinMission(invitationCode: String): NetworkResult<Unit> = handleResult {
        onboardingService.joinMission(JoinMissionRequest(invitationCode))
    }

    override suspend fun getJoinedMissions(): NetworkResult<MissionsResponse> = handleResult {
        onboardingService.getJoinedMissions()
    }
}