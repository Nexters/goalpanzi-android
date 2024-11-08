package com.goalpanzi.mission_mate.core.domain.mission.repository

import com.goalpanzi.mission_mate.core.domain.common.DomainResult
import com.goalpanzi.mission_mate.core.domain.mission.model.MissionBoards
import com.goalpanzi.mission_mate.core.domain.mission.model.MissionDetail
import com.goalpanzi.mission_mate.core.domain.mission.model.MissionRank
import com.goalpanzi.mission_mate.core.domain.mission.model.MissionVerification
import com.goalpanzi.mission_mate.core.domain.mission.model.MissionVerifications
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

    suspend fun completeMission(missionId : Long) : DomainResult<Unit>

    fun clearMissionData() : Flow<Unit>
    fun setIsMissionJoined(data: Boolean) : Flow<Unit>
    fun getIsMissionJoined() : Flow<Boolean?>
}
