package com.goalpanzi.mission_mate.core.navigation

import kotlinx.serialization.Serializable

sealed interface RouteModel {
    @Serializable
    data object Login : RouteModel

    @Serializable
    data class Onboarding(val isAfterProfileCreate: Boolean = false) : RouteModel

    @Serializable
    sealed interface Profile: RouteModel {
        @Serializable
        data object Create : Profile
        @Serializable
        data object Setting : Profile
    }
    @Serializable
    data object Setting : RouteModel
}

sealed interface BoardRouteModel {
    @Serializable
    data class Board(val missionId : Long) : BoardRouteModel

    @Serializable
    data class BoardDetail(val missionId : Long) : BoardRouteModel

    @Serializable
    data class BoardFinish(val missionId : Long) : BoardRouteModel

    @Serializable
    data class UserStory(
        val userCharacter : String,
        val nickname : String,
        val verifiedAt : String,
        val imageUrl : String
    ) : BoardRouteModel

    @Serializable
    data class VerificationPreview(
        val missionId : Long,
        val imageUrl : String
    ) : BoardRouteModel
}

sealed interface OnboardingRouteModel {
    @Serializable
    data object BoardSetup : OnboardingRouteModel

    @Serializable
    data object BoardSetupSuccess : OnboardingRouteModel

    @Serializable
    data object InvitationCode : OnboardingRouteModel
}

sealed interface SettingRouteModel {

    @Serializable
    data object ServicePolicy : SettingRouteModel

    @Serializable
    data object PrivacyPolicy : SettingRouteModel
}
