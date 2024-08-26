package com.goalpanzi.mission_mate.feature.onboarding.model

import com.goalpanzi.core.model.response.MissionResponse

sealed class OnboardingResultEvent {
    data class SuccessWithJoinedMissions(val mission : MissionResponse) : OnboardingResultEvent()
    data object Error : OnboardingResultEvent()
}
