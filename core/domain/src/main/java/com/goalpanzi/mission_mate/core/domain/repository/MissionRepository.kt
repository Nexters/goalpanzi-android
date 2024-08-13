package com.goalpanzi.mission_mate.core.domain.repository

import com.goalpanzi.mission_mate.core.network.ResultHandler
import com.luckyoct.core.model.base.NetworkResult
import com.luckyoct.core.model.response.MissionBoardsResponse
import com.luckyoct.core.model.response.MissionDetailResponse
import com.luckyoct.core.model.response.MissionVerificationsResponse

interface MissionRepository : ResultHandler {
    suspend fun getMissionBoards(missionId : Long) : NetworkResult<MissionBoardsResponse>

    suspend fun getMission(missionId : Long) : NetworkResult<MissionDetailResponse>

    suspend fun getMissionVerifications(missionId: Long) : NetworkResult<MissionVerificationsResponse>

    suspend fun deleteMission(missionId : Long) : NetworkResult<MissionDetailResponse>
}