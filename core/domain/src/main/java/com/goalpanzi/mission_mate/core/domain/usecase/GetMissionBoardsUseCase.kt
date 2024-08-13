package com.goalpanzi.mission_mate.core.domain.usecase

import com.goalpanzi.mission_mate.core.domain.repository.MissionRepository
import com.luckyoct.core.model.base.NetworkResult
import com.luckyoct.core.model.response.MissionBoardsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMissionBoardsUseCase @Inject constructor(
    private val missionRepository: MissionRepository
) {
    operator fun invoke(
        missionId : Long
    ): Flow<NetworkResult<MissionBoardsResponse>> = flow {
        emit(missionRepository.getMissionBoards(missionId))
    }
}