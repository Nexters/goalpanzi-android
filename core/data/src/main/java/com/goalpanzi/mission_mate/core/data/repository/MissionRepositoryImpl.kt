package com.goalpanzi.mission_mate.core.data.repository

import com.goalpanzi.mission_mate.core.domain.repository.MissionRepository
import com.goalpanzi.mission_mate.core.network.service.MissionService
import com.luckyoct.core.model.base.NetworkResult
import com.luckyoct.core.model.response.MissionBoardsResponse
import com.luckyoct.core.model.response.MissionDetailResponse
import javax.inject.Inject

class MissionRepositoryImpl @Inject constructor(
    private val missionService: MissionService
) : MissionRepository {
    override suspend fun getMissionBoards(missionId: Long): NetworkResult<MissionBoardsResponse> = handleResult {
        missionService.getMissionBoards(missionId)
    }

    override suspend fun getMission(missionId: Long): NetworkResult<MissionDetailResponse> = handleResult {
        missionService.getMission(missionId)
    }
}