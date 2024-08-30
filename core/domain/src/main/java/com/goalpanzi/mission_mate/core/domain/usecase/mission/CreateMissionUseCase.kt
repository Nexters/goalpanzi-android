package com.goalpanzi.mission_mate.core.domain.usecase.mission

import com.goalpanzi.mission_mate.core.domain.model.base.DomainResult
import com.goalpanzi.mission_mate.core.domain.model.mission.CreateMissionBody
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionDetail
import com.goalpanzi.mission_mate.core.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateMissionUseCase @Inject constructor(
    private val onboardingRepository: OnboardingRepository
) {
    operator fun invoke(
        createMissionBody : CreateMissionBody
    ): Flow<DomainResult<MissionDetail>> = flow {
        emit(onboardingRepository.createMission(createMissionBody))
    }
}