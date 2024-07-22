package com.goalpanzi.mission_mate.feature.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.goalpanzi.mission_mate.core.navigation.RouteModel

fun NavController.navigateToLogin() {
    this.navigate(RouteModel.Login)
}

fun NavGraphBuilder.loginNavGraph(
    onBackClick: () -> Unit
) {
    composable<RouteModel.Login> {
        LoginRoute(
            onBackClick = onBackClick
        )
    }
}