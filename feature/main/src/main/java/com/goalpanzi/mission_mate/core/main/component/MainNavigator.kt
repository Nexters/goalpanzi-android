package com.goalpanzi.mission_mate.core.main.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.goalpanzi.mission_mate.core.navigation.RouteModel
import com.goalpanzi.mission_mate.feature.board.navigateToBoard
import com.goalpanzi.mission_mate.feature.board.navigateToBoardDetail
import com.goalpanzi.mission_mate.feature.login.navigateToLogin
import com.goalpanzi.mission_mate.feature.onboarding.navigateToBoardSetup
import com.goalpanzi.mission_mate.feature.onboarding.navigateToBoardSetupSuccess
import com.goalpanzi.mission_mate.feature.onboarding.navigateToInvitationCode
import com.goalpanzi.mission_mate.feature.onboarding.navigateToOnboarding
import com.luckyoct.feature.profile.navigateToProfileCreate
import com.luckyoct.feature.profile.navigateToProfileSetting
import com.luckyoct.feature.setting.navigation.navigateToPrivacyPolicy
import com.luckyoct.feature.setting.navigation.navigateToServicePolicy
import com.luckyoct.feature.setting.navigation.navigateToSetting

class MainNavigator(
    val navController: NavHostController
) {

    fun popBackStack() {
        if (navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
            navController.popBackStack()
        }
    }

    fun navigateToLogin() {
        navController.navigateToLogin()
    }

    fun navigateToProfileCreate() {
        navController.navigateToProfileCreate()
    }

    fun navigateToProfileSetting() {
        navController.navigateToProfileSetting()
    }

    fun navigationToOnboarding() {
        navController.navigateToOnboarding()
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

    fun navigationToSetting() {
        navController.navigateToSetting()
    }

    fun navigationToServicePolicy() {
        navController.navigateToServicePolicy()
    }

    fun navigationToPrivacyPolicy() {
        navController.navigateToPrivacyPolicy()
    }

    fun navigationToBoard(missionId : Long) {
        navController.navigateToBoard(missionId)
    }

    fun navigationToBoardDetail(missionId : Long) {
        navController.navigateToBoardDetail(missionId)
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController()
) : MainNavigator = remember(navController) {
    MainNavigator(navController)
}