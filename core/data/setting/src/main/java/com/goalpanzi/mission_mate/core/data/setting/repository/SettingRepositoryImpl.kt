package com.goalpanzi.mission_mate.core.data.setting.repository

import com.goalpanzi.mission_mate.core.datastore.datasource.DefaultDataSource
import com.goalpanzi.mission_mate.core.domain.setting.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val defaultDataSource: DefaultDataSource
) : SettingRepository {

    override fun getViewedTooltip(): Flow<Boolean> = defaultDataSource.getViewedTooltip()

    override fun setViewedTooltip(): Flow<Unit> = defaultDataSource.setViewedTooltip()

}
