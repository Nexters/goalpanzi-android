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
import com.luckyoct.feature.profile.navigateToProfileSetting
import com.luckyoct.feature.setting.navigation.navigateToInquiry
import com.luckyoct.feature.setting.navigation.navigateToPrivacyPolicy
import com.luckyoct.feature.setting.navigation.navigateToServicePolicy
import com.luckyoct.feature.setting.navigation.navigateToSetting

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

    fun navigationToInquiry() {
        navController.navigateToInquiry()
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
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController()
) : MainNavigator = remember(navController) {
    MainNavigator(navController)
}