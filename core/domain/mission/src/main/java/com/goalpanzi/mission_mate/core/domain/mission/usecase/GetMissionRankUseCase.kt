package com.goalpanzi.mission_mate.core.domain.mission.usecase

import com.goalpanzi.mission_mate.core.domain.common.DomainResult
import com.goalpanzi.mission_mate.core.domain.mission.model.MissionRank
import com.goalpanzi.mission_mate.core.domain.mission.repository.MissionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMissionRankUseCase @Inject constructor(
    private val missionRepository: MissionRepository
) {
    operator fun invoke(
        missionId: Long
    ): Flow<DomainResult<MissionRank>> = flow {
        emit(missionRepository.getMissionRank(missionId))
    }
}
