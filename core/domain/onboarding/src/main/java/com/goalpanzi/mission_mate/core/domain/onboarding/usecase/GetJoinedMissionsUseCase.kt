package com.goalpanzi.mission_mate.core.domain.onboarding.usecase

import com.goalpanzi.mission_mate.core.domain.common.DomainResult
import com.goalpanzi.mission_mate.core.domain.onboarding.model.Missions
import com.goalpanzi.mission_mate.core.domain.onboarding.repository.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetJoinedMissionsUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {
    operator fun invoke(filter : String): Flow<DomainResult<Missions>> = flow {
        emit(onboardingRepository.getJoinedMissions(filter))
    }
}
