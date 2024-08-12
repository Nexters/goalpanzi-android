package com.goalpanzi.mission_mate.core.domain.repository

import com.goalpanzi.mission_mate.core.network.ResultHandler
import com.luckyoct.core.model.base.NetworkResult
import com.luckyoct.core.model.response.MissionBoardsResponse
import com.luckyoct.core.model.response.MissionDetailResponse

interface MissionRepository : ResultHandler {
    suspend fun getMissionBoards(missionId : Long) : NetworkResult<MissionBoardsResponse>

    suspend fun getMission(missionId : Long) : NetworkResult<MissionDetailResponse>

}