package com.goalpanzi.mission_mate.core.navigation

import kotlinx.serialization.Serializable

sealed interface RouteModel {
    @Serializable
    data object Login : RouteModel

    @Serializable
    data object Onboarding : RouteModel

    @Serializable
    sealed interface Profile: RouteModel {
        @Serializable
        data object Create : Profile
        @Serializable
        data object Change : Profile
    }
}

sealed interface OnboardingRouteModel {
    @Serializable
    data object BoardSetup : OnboardingRouteModel

    @Serializable
    data object BoardSetupSuccess : OnboardingRouteModel

    @Serializable
    data object InvitationCode : OnboardingRouteModel
}