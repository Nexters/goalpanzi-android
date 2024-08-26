package com.goalpanzi.mission_mate.core.domain.usecase

import com.goalpanzi.mission_mate.core.datastore.datasource.MissionDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetMissionJoinedUseCase @Inject constructor(
    private val missionDataSource: MissionDataSource
) {
    operator fun invoke(
        isJoined : Boolean
    ): Flow<Unit> = missionDataSource.setIsMissionJoined(isJoined)
}