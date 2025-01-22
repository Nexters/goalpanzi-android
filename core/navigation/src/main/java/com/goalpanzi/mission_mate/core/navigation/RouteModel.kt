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
    sealed interface MainTabRoute : RouteModel {
        @Serializable
        sealed interface MissionRouteModel : MainTabRoute {
            @Serializable
            data class Onboarding(val isAfterProfileCreate: Boolean = false) : MissionRouteModel

            @Serializable
            data object BoardSetup : MissionRouteModel

            @Serializable
            data object BoardSetupSuccess : MissionRouteModel

            @Serializable
            data object InvitationCode : MissionRouteModel

            @Serializable
            data class Board(val missionId : Long) : MissionRouteModel

            @Serializable
            data class Detail(val missionId : Long) : MissionRouteModel

            @Serializable
            data class Finish(val missionId : Long) : MissionRouteModel

            @Serializable
            data class UserStory(
                val userCharacter : String,
                val nickname : String,
                val verifiedAt : String,
                val imageUrl : String
            ) : MissionRouteModel

            @Serializable
            data class VerificationPreview(
                val missionId : Long,
                val imageUrl : String
            ) : MissionRouteModel
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
