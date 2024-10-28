package com.goalpanzi.mission_mate.core.domain.onboarding.repository

import com.goalpanzi.mission_mate.core.domain.common.DomainResult
import com.goalpanzi.mission_mate.core.domain.mission.model.MissionDetail
import com.goalpanzi.mission_mate.core.domain.onboarding.model.Missions
import com.goalpanzi.mission_mate.core.domain.onboarding.model.CreateMissionBody

interface OnboardingRepository  {
    suspend fun createMission(
        createMissionBody: CreateMissionBody
    ): DomainResult<MissionDetail>

    suspend fun getMissionByInvitationCode(
        invitationCode : String
    ) : DomainResult<MissionDetail>

    suspend fun joinMission(
        invitationCode: String
    ) : DomainResult<Unit>

    suspend fun getJoinedMissions() : DomainResult<Missions>
}
