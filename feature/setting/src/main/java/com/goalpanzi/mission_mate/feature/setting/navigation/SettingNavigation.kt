package com.goalpanzi.mission_mate.feature.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.goalpanzi.mission_mate.core.navigation.model.RouteModel.MainTabRoute.SettingRouteModel
import com.goalpanzi.mission_mate.core.ui.util.slideInFromEnd
import com.goalpanzi.mission_mate.feature.setting.screen.SettingRoute
import com.goalpanzi.mission_mate.feature.setting.screen.WebViewScreen

fun NavController.navigateToSetting(
    navOptions: NavOptions? = androidx.navigation.navOptions {
        popUpTo(this@navigateToSetting.graph.id) {
            inclusive = true
        }
    }
) {
    this.navigate(SettingRouteModel.Setting,navOptions = navOptions)
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
    composable<SettingRouteModel.Setting> {
        SettingRoute(
            onBackClick = onBackClick,
            onClickProfileSetting = onClickProfileSetting,
            onClickServicePolicy = onClickServicePolicy,
            onClickPrivacyPolicy = onClickPrivacyPolicy,
            onLogout = onClickLogout
        )
    }
}


fun NavGraphBuilder.servicePolicyNavGraph(
    onBackClick: () -> Unit
) {
    composable<SettingRouteModel.ServicePolicy>(
        enterTransition = {
            slideInFromEnd()
        },
        popEnterTransition = null,
    ) {
        WebViewScreen(
            onBackClick = onBackClick,
            url = "https://missionmate.notion.site/f638866edeaf45b58ef63d1000f30c15?pvs=73"
        )
    }
}

fun NavGraphBuilder.privacyPolicyNavGraph(
    onBackClick: () -> Unit
) {
    composable<SettingRouteModel.PrivacyPolicy>(
        enterTransition = {
            slideInFromEnd()
        },
        popEnterTransition = null,
    ) {
        WebViewScreen(
            onBackClick = onBackClick,
            url = "https://missionmate.notion.site/c79e9e6990de466490c584f351b364b7?pvs=4"
        )
    }
}
