package com.goalpanzi.mission_mate.core.domain.usecase

import com.goalpanzi.mission_mate.core.domain.repository.OnboardingRepository
import com.luckyoct.core.model.base.NetworkResult
import com.luckyoct.core.model.response.MissionsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetJoinedMissionsUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {
    operator fun invoke(): Flow<NetworkResult<MissionsResponse>> = flow {
        emit(onboardingRepository.getJoinedMissions())
    }
}