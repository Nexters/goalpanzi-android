package com.goalpanzi.mission_mate.core.main.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.goalpanzi.mission_mate.core.navigation.RouteModel
import com.goalpanzi.mission_mate.feature.board.navigateToBoard
import com.goalpanzi.mission_mate.feature.login.navigateToLogin
import com.goalpanzi.mission_mate.feature.onboarding.navigateToBoardSetup
import com.goalpanzi.mission_mate.feature.onboarding.navigateToBoardSetupSuccess
import com.goalpanzi.mission_mate.feature.onboarding.navigateToInvitationCode
import com.goalpanzi.mission_mate.feature.onboarding.navigateToOnboarding
import com.luckyoct.feature.profile.navigateToProfileCreate

class MainNavigator(
    val navController: NavHostController
) {

    //TODO : change to Main
    val startDestination = RouteModel.Board

    fun popBackStack() {
        navController.popBackStack()
    }

    fun navigateToLogin() {
        navController.navigateToLogin()
    }

    fun navigateToProfileCreate() {
        navController.navigateToProfileCreate()
    }

    fun navigationToOnboarding(
        navOptions: NavOptions? = null
    ) {
        navController.navigateToOnboarding(
            navOptions = navOptions
        )
    }

    fun navigationToBoardSetup() {
        navController.navigateToBoardSetup()
    }

    fun navigationToBoardSetupSuccess() {
        navController.navigateToBoardSetupSuccess()
    }

    fun navigationToInvitationCode() {
        navController.navigateToInvitationCode()
    }

    fun navigationToBoard() {
        navController.navigateToBoard()
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController()
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}