package com.goalpanzi.mission_mate.core.datastore.datasource

import kotlinx.coroutines.flow.Flow

interface DefaultDataSource {
    fun clearUserData() : Flow<Unit>
}