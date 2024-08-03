package com.goalpanzi.mission_mate.core.navigation

import kotlinx.serialization.Serializable

sealed interface RouteModel {
    @Serializable
    data object Login : RouteModel

    @Serializable
    data object Onboarding : RouteModel
}

sealed interface OnboardingRouteModel {
    @Serializable
    data object BoardSetup : OnboardingRouteModel

    @Serializable
    data object BoardSetupSuccess : OnboardingRouteModel

    @Serializable
    data object InvitationCode : OnboardingRouteModel
}