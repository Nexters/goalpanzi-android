package com.goalpanzi.mission_mate.core.main.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.goalpanzi.mission_mate.core.navigation.RouteModel
import com.goalpanzi.mission_mate.feature.login.navigateToLogin

class MainNavigator(
    val navController: NavHostController
) {

    //TODO : change to Main
    val startDestination = RouteModel.Login

    fun popBackStack() {
        navController.popBackStack()
    }

    fun navigateToLogin() {
        navController.navigateToLogin()
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController()
) : MainNavigator = remember(navController) {
    MainNavigator(navController)
}