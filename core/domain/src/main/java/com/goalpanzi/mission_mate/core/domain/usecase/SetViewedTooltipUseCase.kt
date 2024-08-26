package com.goalpanzi.mission_mate.core.domain.usecase

import com.goalpanzi.mission_mate.core.datastore.datasource.DefaultDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetViewedTooltipUseCase @Inject constructor(
    private val defaultDataSource: DefaultDataSource
) {
    operator fun invoke(): Flow<Unit> = defaultDataSource.setViewedTooltip()
}