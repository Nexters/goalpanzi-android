package com.goalpanzi.mission_mate.core.domain.repository

import com.goalpanzi.mission_mate.core.domain.model.base.DomainResult
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionBoards
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionDetail
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionRank
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionVerification
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionVerifications
import kotlinx.coroutines.flow.Flow
import java.io.File

interface MissionRepository  {
    suspend fun getMissionBoards(missionId : Long) : DomainResult<MissionBoards>

    suspend fun getMission(missionId : Long) : DomainResult<MissionDetail>

    suspend fun getMissionVerifications(missionId: Long) : DomainResult<MissionVerifications>

    suspend fun deleteMission(missionId : Long) : DomainResult<MissionDetail>

    suspend fun getMissionRank(missionId: Long) : DomainResult<MissionRank>

    suspend fun verifyMission(missionId: Long, image: File) : DomainResult<Unit>

    suspend fun getMyMissionVerification(missionId: Long, number : Int) : DomainResult<MissionVerification>

    fun clearMissionData() : Flow<Unit>
    fun setIsMissionJoined(data: Boolean) : Flow<Unit>
    fun getIsMissionJoined() : Flow<Boolean?>
}