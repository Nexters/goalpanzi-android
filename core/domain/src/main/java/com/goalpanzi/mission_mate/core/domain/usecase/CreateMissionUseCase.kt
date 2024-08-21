package com.goalpanzi.mission_mate.core.domain.usecase

import com.goalpanzi.mission_mate.core.domain.repository.OnboardingRepository
import com.goalpanzi.core.model.base.NetworkResult
import com.goalpanzi.core.model.request.CreateMissionRequest
import com.goalpanzi.core.model.response.MissionDetailResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateMissionUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {
    operator fun invoke(
        createMissionRequest : CreateMissionRequest
    ): Flow<NetworkResult<MissionDetailResponse>> = flow {
        emit(onboardingRepository.createMission(createMissionRequest))
    }
}