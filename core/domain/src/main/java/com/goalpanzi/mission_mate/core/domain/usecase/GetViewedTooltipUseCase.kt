package com.goalpanzi.mission_mate.core.domain.usecase

import com.goalpanzi.mission_mate.core.domain.repository.DefaultRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetViewedTooltipUseCase @Inject constructor(
    private val defaultRepository: DefaultRepository
) {
    operator fun invoke(): Flow<Boolean> = defaultRepository.getViewedTooltip()
}