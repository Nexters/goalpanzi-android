package com.luckyoct.feature.profile

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
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
    composable<RouteModel.Profile.Create>(
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(300)
            )
        },
        content = {
            ProfileRoute(
                profileSettingType = ProfileSettingType.CREATE,
                onSaveSuccess = onSaveSuccess
            )
        }
    )
    composable<RouteModel.Profile.Setting> {
        ProfileRoute(
            profileSettingType = ProfileSettingType.SETTING,
            onSaveSuccess = onBackClick,
            onBackClick = onBackClick
        )
    }
}