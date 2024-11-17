package com.goalpanzi.mission_mate.core.domain.mission.usecase

import com.goalpanzi.mission_mate.core.domain.common.DomainResult
import com.goalpanzi.mission_mate.core.domain.mission.repository.MissionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class VerifyMissionUseCase @Inject constructor(
    private val missionRepository: MissionRepository
) {
    operator fun invoke(missionId: Long, image: File) : Flow<DomainResult<Unit>> = flow {
        emit(missionRepository.verifyMission(missionId, image))
    }
}
