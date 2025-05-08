package com.goalpanzi.mission_mate.feature.main.component

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.goalpanzi.mission_mate.core.navigation.model.MainTabDataModel
import com.goalpanzi.mission_mate.core.navigation.model.RouteModel
import com.goalpanzi.mission_mate.feature.board.verification.model.MyVerificationExtra
import com.goalpanzi.mission_mate.feature.board.model.UserStory
import com.goalpanzi.mission_mate.feature.board.navigateToBoardDetail
import com.goalpanzi.mission_mate.feature.board.navigateToBoardFinish
import com.goalpanzi.mission_mate.feature.board.navigateToMyVerificationHistory
import com.goalpanzi.mission_mate.feature.board.navigateToUserStory
import com.goalpanzi.mission_mate.feature.board.navigateToVerificationPreview
import com.goalpanzi.mission_mate.feature.login.navigateToLogin
import com.goalpanzi.mission_mate.feature.onboarding.navigateToBoardSetup
import com.goalpanzi.mission_mate.feature.onboarding.navigateToBoardSetupSuccess
import com.goalpanzi.mission_mate.feature.onboarding.navigateToInvitationCode
import com.goalpanzi.mission_mate.feature.profile.navigateToProfileCreate
import com.goalpanzi.mission_mate.feature.profile.navigateToProfileSetting
import com.goalpanzi.mission_mate.feature.setting.navigation.navigateToPrivacyPolicy
import com.goalpanzi.mission_mate.feature.setting.navigation.navigateToServicePolicy

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

    fun navigationToMainTab(
        mainTabDataModel: MainTabDataModel = MainTabDataModel.Mission()
    ) {
        navController.navigate(
            route = RouteModel.MainTab(mainTabDataModel)
        ){
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
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

    fun navigationToServicePolicy() {
        navController.navigateToServicePolicy()
    }

    fun navigationToPrivacyPolicy() {
        navController.navigateToPrivacyPolicy()
    }

    fun navigationToBoardDetail(missionId : Long) {
        navController.navigateToBoardDetail(missionId)
    }
    fun navigateToBoardFinish(missionId : Long){
        navController.navigateToBoardFinish(missionId)
    }

    fun navigationToUserStory(userStory: UserStory) {
        navController.navigateToUserStory(userStory.characterUiModelType)
    }

    fun navigationToVerificationPreview(missionId: Long, imageUrl : Uri) {
        navController.navigateToVerificationPreview(missionId, imageUrl)
    }

    fun navigationToMyVerification(extra: MyVerificationExtra) {
        navController.navigateToMyVerificationHistory(extra)
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController()
) : MainNavigator = remember(navController) {
    MainNavigator(navController)
}

