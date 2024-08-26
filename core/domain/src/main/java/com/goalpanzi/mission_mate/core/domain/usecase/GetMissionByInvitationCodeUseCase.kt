package com.goalpanzi.mission_mate.core.domain.usecase

import com.goalpanzi.mission_mate.core.domain.repository.OnboardingRepository
import com.goalpanzi.core.model.base.NetworkResult
import com.goalpanzi.core.model.response.MissionDetailResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMissionByInvitationCodeUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {
    operator fun invoke(
        invitationCode: String
    ): Flow<NetworkResult<MissionDetailResponse>> = flow {
        emit(onboardingRepository.getMissionByInvitationCode(invitationCode))
    }
}