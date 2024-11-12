package com.goalpanzi.mission_mate.core.domain.mission.usecase

import com.goalpanzi.mission_mate.core.domain.common.DomainResult
import com.goalpanzi.mission_mate.core.domain.mission.model.MissionVerification
import com.goalpanzi.mission_mate.core.domain.mission.repository.MissionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ViewVerificationUseCase @Inject constructor(
    private val missionRepository: MissionRepository
) {
    operator fun invoke(missionVerificationId: Long) : Flow<DomainResult<MissionVerification>> = flow {
        emit(missionRepository.viewVerification(missionVerificationId))
    }
}

