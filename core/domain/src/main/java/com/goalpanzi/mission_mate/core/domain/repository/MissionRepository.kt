package com.goalpanzi.mission_mate.core.domain.repository

import com.goalpanzi.mission_mate.core.network.ResultHandler
import com.goalpanzi.core.model.base.NetworkResult
import com.goalpanzi.core.model.response.MissionBoardsResponse
import com.goalpanzi.core.model.response.MissionDetailResponse
import com.goalpanzi.core.model.response.MissionRankResponse
import com.goalpanzi.core.model.response.MissionVerificationResponse
import com.goalpanzi.core.model.response.MissionVerificationsResponse
import java.io.File

interface MissionRepository : ResultHandler {
    suspend fun getMissionBoards(missionId : Long) : NetworkResult<MissionBoardsResponse>

    suspend fun getMission(missionId : Long) : NetworkResult<MissionDetailResponse>

    suspend fun getMissionVerifications(missionId: Long) : NetworkResult<MissionVerificationsResponse>

    suspend fun deleteMission(missionId : Long) : NetworkResult<MissionDetailResponse>

    suspend fun getMissionRank(missionId: Long) : NetworkResult<MissionRankResponse>

    suspend fun verifyMission(missionId: Long, image: File) : NetworkResult<Unit>

    suspend fun getMyMissionVerification(missionId: Long, number : Int) : NetworkResult<MissionVerificationResponse>
}