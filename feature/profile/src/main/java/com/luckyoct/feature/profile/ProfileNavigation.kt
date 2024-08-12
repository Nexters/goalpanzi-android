package com.luckyoct.feature.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.goalpanzi.mission_mate.core.navigation.RouteModel

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
    composable<RouteModel.Profile.Create> {
        ProfileRoute(
            profileSettingType = ProfileSettingType.CREATE,
            onSaveSuccess = { onSaveSuccess() }
        )
    }
    composable<RouteModel.Profile.Setting> {
        ProfileRoute(
            profileSettingType = ProfileSettingType.SETTING,
            onSaveSuccess = { onSaveSuccess() },
            onBackClick = { onBackClick() }
        )
    }
}