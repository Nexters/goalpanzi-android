package com.goalpanzi.mission_mate.feature.onboarding.model

import com.goalpanzi.mission_mate.core.domain.common.model.user.UserProfile


sealed class OnboardingUiModel {
    data object Loading : OnboardingUiModel()
    data class Success(val profileResponse: UserProfile) : OnboardingUiModel()
}
