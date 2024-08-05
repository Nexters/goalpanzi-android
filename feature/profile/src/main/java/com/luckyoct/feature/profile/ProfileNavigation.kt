package com.luckyoct.feature.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.goalpanzi.mission_mate.core.navigation.RouteModel

enum class ProfileSettingType {
    CREATE, CHANGE
}

fun NavController.navigateToProfileCreate() {
    this.navigate(RouteModel.Profile.Create)
}

fun NavController.navigateToProfileChange() {
    this.navigate(RouteModel.Profile.Change)
}

fun NavGraphBuilder.profileNavGraph(
    onSaveSuccess: () -> Unit
) {
    composable<RouteModel.Profile.Create> {
        ProfileRoute(
            profileSettingType = ProfileSettingType.CREATE,
            onSaveSuccess = { onSaveSuccess() }
        )
    }
    composable<RouteModel.Profile.Change> {
        ProfileRoute(
            profileSettingType = ProfileSettingType.CHANGE,
            onSaveSuccess = { onSaveSuccess() }
        )
    }
}