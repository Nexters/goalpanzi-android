package com.goalpanzi.mission_mate.feature.onboarding.model

import com.luckyoct.core.model.response.ProfileResponse

sealed class OnboardingUiModel {
    data object Loading : OnboardingUiModel()
    data class Success(val profileResponse: ProfileResponse) : OnboardingUiModel()
}
