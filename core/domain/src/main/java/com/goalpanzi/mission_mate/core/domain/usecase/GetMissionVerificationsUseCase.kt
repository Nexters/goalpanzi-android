package com.goalpanzi.mission_mate.core.domain.usecase

import com.goalpanzi.mission_mate.core.domain.repository.MissionRepository
import com.goalpanzi.core.model.base.NetworkResult
import com.goalpanzi.core.model.response.MissionVerificationsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMissionVerificationsUseCase @Inject constructor(
    private val missionRepository: MissionRepository
) {
    operator fun invoke(
        missionId : Long
    ): Flow<NetworkResult<MissionVerificationsResponse>> = flow {
        emit(missionRepository.getMissionVerifications(missionId))
    }
}