package com.luckyoct.feature.setting.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.goalpanzi.mission_mate.core.navigation.RouteModel
import com.goalpanzi.mission_mate.core.navigation.SettingRouteModel
import com.luckyoct.feature.setting.screen.SettingRoute
import com.luckyoct.feature.setting.screen.WebViewScreen

fun NavController.navigateToSetting() {
    this.navigate(RouteModel.Setting)
}

fun NavController.navigateToServicePolicy() {
    this.navigate(SettingRouteModel.ServicePolicy)
}

fun NavController.navigateToPrivacyPolicy() {
    this.navigate(SettingRouteModel.PrivacyPolicy)
}

fun NavGraphBuilder.settingNavGraph(
    onBackClick: () -> Unit,
    onClickProfileSetting: () -> Unit,
    onClickServicePolicy: () -> Unit,
    onClickPrivacyPolicy: () -> Unit,
    onClickLogout: () -> Unit
) {
    composable<RouteModel.Setting>(
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(300)
            )
        },
        popEnterTransition = null,
        content = {
            SettingRoute(
                onBackClick = onBackClick,
                onClickProfileSetting = onClickProfileSetting,
                onClickServicePolicy = onClickServicePolicy,
                onClickPrivacyPolicy = onClickPrivacyPolicy,
                onLogout = onClickLogout
            )
        })
}

fun NavGraphBuilder.servicePolicyNavGraph(
    onBackClick: () -> Unit
) {
    composable<SettingRouteModel.ServicePolicy> {
        WebViewScreen(
            onBackClick = onBackClick,
            url = "https://missionmate.notion.site/f638866edeaf45b58ef63d1000f30c15?pvs=73"
        )
    }
}

fun NavGraphBuilder.privacyPolicyNavGraph(
    onBackClick: () -> Unit
) {
    composable<SettingRouteModel.PrivacyPolicy> {
        WebViewScreen(
            onBackClick = onBackClick,
            url = "https://missionmate.notion.site/c79e9e6990de466490c584f351b364b7?pvs=4"
        )
    }
}