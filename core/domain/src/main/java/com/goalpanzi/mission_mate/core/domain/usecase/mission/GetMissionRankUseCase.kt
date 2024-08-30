package com.goalpanzi.mission_mate.core.domain.usecase.mission

import com.goalpanzi.mission_mate.core.domain.model.base.DomainResult
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionRank
import com.goalpanzi.mission_mate.core.domain.repository.MissionRepository
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