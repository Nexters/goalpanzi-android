package com.goalpanzi.mission_mate.core.domain.usecase.mission

import com.goalpanzi.mission_mate.core.domain.repository.OnboardingRepository
import com.goalpanzi.mission_mate.core.domain.model.base.DomainResult
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