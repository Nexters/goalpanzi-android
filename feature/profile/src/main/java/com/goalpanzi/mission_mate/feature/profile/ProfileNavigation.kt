package com.goalpanzi.mission_mate.feature.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.goalpanzi.mission_mate.core.navigation.model.RouteModel
import com.goalpanzi.mission_mate.core.ui.util.slideInFromEnd
import com.goalpanzi.mission_mate.core.ui.util.slideOutToEnd

enum class ProfileSettingType {
    CREATE, SETTING
}

fun NavController.navigateToProfileCreate() {
    this.navigate(RouteModel.Profile.Create)
}

fun NavController.navigateToProfileSetting() {
    this.navigate(RouteModel.Profile.Setting)
}

fun NavGraphBuilder.profileNavGraph(
    onSaveSuccess: () -> Unit,
    onBackClick: () -> Unit
) {
    composable<RouteModel.Profile.Create>(
        enterTransition = {
            slideInFromEnd()
        },
        popExitTransition = {
            slideOutToEnd()
        }
    ) {
        ProfileRoute(
            profileSettingType = ProfileSettingType.CREATE,
            onSaveSuccess = onSaveSuccess
        )
    }
    composable<RouteModel.Profile.Setting> (
        enterTransition = {
            slideInFromEnd()
        },
        popExitTransition = {
            slideOutToEnd()
        }
    ){
        ProfileRoute(
            profileSettingType = ProfileSettingType.SETTING,
            onSaveSuccess = onBackClick,
            onBackClick = onBackClick
        )
    }
}
