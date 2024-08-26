package com.goalpanzi.mission_mate.core.datastore.datasource

import kotlinx.coroutines.flow.Flow

interface MissionDataSource {
    fun clearMissionData() : Flow<Unit>
    fun setIsMissionJoined(data: Boolean) : Flow<Unit>
    fun getIsMissionJoined() : Flow<Boolean?>
}