package com.goalpanzi.mission_mate.core.domain.usecase.mission

import com.goalpanzi.mission_mate.core.domain.repository.MissionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetMissionJoinedUseCase @Inject constructor(
    private val missionRepository: MissionRepository
) {
    operator fun invoke(
        isJoined : Boolean
    ): Flow<Unit> = missionRepository.setIsMissionJoined(isJoined)
}