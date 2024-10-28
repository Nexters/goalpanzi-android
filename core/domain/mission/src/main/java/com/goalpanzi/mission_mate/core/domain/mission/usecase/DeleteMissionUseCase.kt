package com.goalpanzi.mission_mate.core.domain.mission.usecase

import com.goalpanzi.mission_mate.core.domain.common.DomainResult
import com.goalpanzi.mission_mate.core.domain.mission.model.MissionDetail
import com.goalpanzi.mission_mate.core.domain.mission.repository.MissionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteMissionUseCase @Inject constructor(
    private val missionRepository: MissionRepository
) {
    operator fun invoke(
        missionId: Long
    ): Flow<DomainResult<MissionDetail>> = flow {
        emit(missionRepository.deleteMission(missionId))
    }
}
