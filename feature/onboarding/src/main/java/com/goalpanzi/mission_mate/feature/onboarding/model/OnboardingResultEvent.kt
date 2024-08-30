package com.goalpanzi.mission_mate.feature.onboarding.model

import com.goalpanzi.mission_mate.core.domain.model.mission.Mission

sealed class OnboardingResultEvent {
    data class SuccessWithJoinedMissions(val mission : Mission) : OnboardingResultEvent()
    data object Error : OnboardingResultEvent()
}
