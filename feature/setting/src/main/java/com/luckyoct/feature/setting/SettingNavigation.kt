package com.luckyoct.feature.setting

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.goalpanzi.mission_mate.core.navigation.RouteModel

fun NavController.navigateToSetting() {
    this.navigate(RouteModel.Setting)
}

fun NavGraphBuilder.settingNavGraph() {
    composable<RouteModel.Setting> {
        SettingRoute()
    }
}