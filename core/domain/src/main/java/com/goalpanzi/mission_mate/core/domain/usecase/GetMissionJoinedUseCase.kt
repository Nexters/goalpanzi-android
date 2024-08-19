package com.goalpanzi.mission_mate.core.domain.usecase

import com.goalpanzi.mission_mate.core.datastore.datasource.MissionDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMissionJoinedUseCase @Inject constructor(
    private val missionDataSource: MissionDataSource
) {
    operator fun invoke(): Flow<Boolean?> = missionDataSource.getIsMissionJoined()
}