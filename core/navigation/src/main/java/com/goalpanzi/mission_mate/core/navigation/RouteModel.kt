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
    sealed interface OnboardingRouteModel {
        @Serializable
        data class Onboarding(val isAfterProfileCreate: Boolean = false) : RouteModel

        @Serializable
        data object BoardSetup : OnboardingRouteModel

        @Serializable
        data object BoardSetupSuccess : OnboardingRouteModel

        @Serializable
        data object InvitationCode : OnboardingRouteModel
    }

    @Serializable
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

    @Serializable
    sealed interface SettingRouteModel {

        @Serializable
        data object Setting : SettingRouteModel

        @Serializable
        data object ServicePolicy : SettingRouteModel

        @Serializable
        data object PrivacyPolicy : SettingRouteModel
    }
}

fun RouteModel.fullPathName(): String? {
    return this::class.qualifiedName
}
