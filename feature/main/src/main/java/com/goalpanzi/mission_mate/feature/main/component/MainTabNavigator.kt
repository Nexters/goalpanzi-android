package com.goalpanzi.mission_mate.feature.main.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.goalpanzi.mission_mate.feature.board.navigateToBoard
import com.goalpanzi.mission_mate.feature.history.navigateToHistory
import com.goalpanzi.mission_mate.feature.onboarding.navigateToOnboarding
import com.goalpanzi.mission_mate.feature.setting.navigation.navigateToSetting

class MainTabNavigator(
    val navController: NavHostController
) {
    fun navigationToOnboarding(isAfterProfileCreate: Boolean = false) {
        navController.navigateToOnboarding(isAfterProfileCreate)
    }

    fun navigationToHistory() {
        navController.navigateToHistory()
    }

    fun navigationToSetting() {
        navController.navigateToSetting()
    }
    fun navigationToBoard(missionId : Long) {
        navController.navigateToBoard(missionId)
    }
}

@Composable
internal fun rememberMainTabNavigator(
    navController: NavHostController = rememberNavController()
) : MainTabNavigator = remember(navController) {
    MainTabNavigator(navController)
}

