package com.goalpanzi.mission_mate.core.domain.onboarding.usecase

import com.goalpanzi.mission_mate.core.domain.common.DomainResult
import com.goalpanzi.mission_mate.core.domain.onboarding.repository.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JoinMissionUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {
    operator fun invoke(
        invitationCode: String
    ): Flow<DomainResult<Unit>> = flow {
        emit(onboardingRepository.joinMission(invitationCode))
    }
}
