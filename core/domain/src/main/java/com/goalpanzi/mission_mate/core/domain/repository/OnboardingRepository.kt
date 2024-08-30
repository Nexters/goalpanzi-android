package com.goalpanzi.mission_mate.core.domain.repository

import com.goalpanzi.mission_mate.core.domain.model.base.DomainResult
import com.goalpanzi.mission_mate.core.domain.model.mission.CreateMissionBody
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionDetail
import com.goalpanzi.mission_mate.core.domain.model.mission.Missions

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