package com.goalpanzi.mission_mate.core.domain.usecase

import com.goalpanzi.mission_mate.core.domain.repository.MissionRepository
import com.goalpanzi.core.model.base.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class VerifyMissionUseCase @Inject constructor(
    private val missionRepository: MissionRepository
) {
    suspend operator fun invoke(missionId: Long, image: File) : Flow<NetworkResult<Unit>> = flow {
        emit(missionRepository.verifyMission(missionId, image))
    }
}