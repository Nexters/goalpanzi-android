package com.luckyoct.feature.setting.navigation

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

fun NavController.navigateToInquiry() {
    this.navigate(SettingRouteModel.Inquiry)
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
    onClickInquiry: () -> Unit,
    onClickServicePolicy: () -> Unit,
    onClickPrivacyPolicy: () -> Unit,
    onClickLogout: () -> Unit
) {
    composable<RouteModel.Setting> {
        SettingRoute(
            onBackClick = onBackClick,
            onClickProfileSetting = onClickProfileSetting,
            onClickInquiry = onClickInquiry,
            onClickServicePolicy = onClickServicePolicy,
            onClickPrivacyPolicy = onClickPrivacyPolicy,
            onLogout = onClickLogout
        )
    }
}

fun NavGraphBuilder.inquiryNavGraph(
    onBackClick: () -> Unit
) {
    composable<SettingRouteModel.Inquiry> {
        WebViewScreen(
            onBackClick = onBackClick,
            url = "https://www.google.com/"
        )
    }
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