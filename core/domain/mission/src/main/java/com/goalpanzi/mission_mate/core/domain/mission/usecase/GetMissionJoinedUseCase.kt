package com.goalpanzi.mission_mate.core.domain.mission.usecase

import com.goalpanzi.mission_mate.core.domain.mission.repository.MissionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMissionJoinedUseCase @Inject constructor(
    private val missionRepository: MissionRepository
) {
    operator fun invoke(): Flow<Boolean?> = missionRepository.getIsMissionJoined()
}
