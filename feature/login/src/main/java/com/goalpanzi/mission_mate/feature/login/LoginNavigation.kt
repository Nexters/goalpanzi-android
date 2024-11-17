package com.goalpanzi.mission_mate.feature.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavController.navigateToLogin() {
    this.navigate("RouteModel.Login") {
        popUpTo(this@navigateToLogin.graph.id){
            inclusive = true
        }
    }
}

fun NavGraphBuilder.loginNavGraph(
    onLoginSuccess: (isProfileSet: Boolean) -> Unit
) {
    composable("RouteModel.Login") {
        LoginRoute(
            onLoginSuccess = onLoginSuccess
        )
    }
}