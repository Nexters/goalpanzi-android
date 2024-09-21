package com.goalpanzi.mission_mate.core.domain.setting.usecase

import com.goalpanzi.mission_mate.core.domain.setting.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetViewedTooltipUseCase @Inject constructor(
    private val settingRepository: SettingRepository
) {
    operator fun invoke(): Flow<Boolean> = settingRepository.getViewedTooltip()
}