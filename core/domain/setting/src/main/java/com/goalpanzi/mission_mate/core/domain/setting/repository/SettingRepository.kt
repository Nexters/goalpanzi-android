package com.goalpanzi.mission_mate.core.domain.setting.repository

import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    fun getViewedTooltip() : Flow<Boolean>
    fun setViewedTooltip() : Flow<Unit>
}
