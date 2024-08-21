package com.goalpanzi.mission_mate.core.domain.usecase

import com.goalpanzi.mission_mate.core.domain.repository.MissionRepository
import com.goalpanzi.core.model.base.NetworkResult
import com.goalpanzi.core.model.response.MissionDetailResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteMissionUseCase @Inject constructor(
    private val missionRepository: MissionRepository
) {
    operator fun invoke(
        missionId: Long
    ): Flow<NetworkResult<MissionDetailResponse>> = flow {
        emit(missionRepository.deleteMission(missionId))
    }
}