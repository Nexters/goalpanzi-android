package com.goalpanzi.mission_mate.core.domain.onboarding.usecase

import com.goalpanzi.mission_mate.core.domain.common.DomainResult
import com.goalpanzi.mission_mate.core.domain.mission.model.MissionDetail
import com.goalpanzi.mission_mate.core.domain.onboarding.repository.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMissionByInvitationCodeUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {
    operator fun invoke(
        invitationCode: String
    ): Flow<DomainResult<MissionDetail>> = flow {
        emit(onboardingRepository.getMissionByInvitationCode(invitationCode))
    }
}
