package com.goalpanzi.mission_mate.core.navigation

import kotlinx.serialization.Serializable


sealed interface RouteModel {
    @Serializable
    data object Login : RouteModel

    @Serializable
    sealed interface Profile: RouteModel {
        @Serializable
        data object Create : Profile
        @Serializable
        data object Setting : Profile
    }

    @Serializable
    sealed interface Mission: RouteModel {
        @Serializable
        data class Board(val missionId : Long) : Mission

        @Serializable
        data class Detail(val missionId : Long) : Mission

        @Serializable
        data class Finish(val missionId : Long) : Mission

        @Serializable
        data class UserStory(
            val userCharacter : String,
            val nickname : String,
            val verifiedAt : String,
            val imageUrl : String
        ) : Mission

        @Serializable
        data class VerificationPreview(
            val missionId : Long,
            val imageUrl : String
        ) : Mission
    }

    @Serializable
    sealed interface MainTabRoute : RouteModel {
        @Serializable
        sealed interface OnboardingRouteModel : MainTabRoute {
            @Serializable
            data class Onboarding(val isAfterProfileCreate: Boolean = false) : OnboardingRouteModel

            @Serializable
            data object BoardSetup : OnboardingRouteModel

            @Serializable
            data object BoardSetupSuccess : OnboardingRouteModel

            @Serializable
            data object InvitationCode : OnboardingRouteModel
        }

        @Serializable
        sealed interface HistoryRouteModel : MainTabRoute

        @Serializable
        sealed interface SettingRouteModel : MainTabRoute {
            @Serializable
            data object Setting : SettingRouteModel

            @Serializable
            data object ServicePolicy : SettingRouteModel

            @Serializable
            data object PrivacyPolicy : SettingRouteModel
        }
    }
}

fun RouteModel.fullPathName(): String? {
    return this::class.qualifiedName
}
