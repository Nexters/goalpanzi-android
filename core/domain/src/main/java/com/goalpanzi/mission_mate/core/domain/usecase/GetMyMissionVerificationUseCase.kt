package com.goalpanzi.mission_mate.core.domain.usecase

import com.goalpanzi.mission_mate.core.domain.repository.MissionRepository
import com.luckyoct.core.model.base.NetworkResult
import com.luckyoct.core.model.response.MissionVerificationResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMyMissionVerificationUseCase @Inject constructor(
    private val missionRepository: MissionRepository
) {
    operator fun invoke(
        missionId: Long,
        number : Int
    ): Flow<NetworkResult<MissionVerificationResponse>> = flow {
        emit(missionRepository.getMyMissionVerification(missionId,number))
    }
}