package com.goalpanzi.mission_mate.core.domain.usecase.mission

import com.goalpanzi.mission_mate.core.domain.repository.MissionRepository
import com.goalpanzi.mission_mate.core.domain.model.base.DomainResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class VerifyMissionUseCase @Inject constructor(
    private val missionRepository: MissionRepository
) {
    suspend operator fun invoke(missionId: Long, image: File) : Flow<DomainResult<Unit>> = flow {
        emit(missionRepository.verifyMission(missionId, image))
    }
}