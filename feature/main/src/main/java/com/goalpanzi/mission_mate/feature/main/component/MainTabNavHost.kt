package com.goalpanzi.mission_mate.feature.main.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.goalpanzi.mission_mate.core.navigation.model.RouteModel.MainTabRoute.MissionRouteModel
import com.goalpanzi.mission_mate.feature.board.boardNavGraph
import com.goalpanzi.mission_mate.feature.onboarding.onboardingNavGraph
import com.goalpanzi.mission_mate.feature.setting.navigation.settingNavGraph

@Composable
fun MainTabNavHost(
    mainNavigator: MainNavigator,
    mainTabNavigator : MainTabNavigator,
    modifier: Modifier = Modifier
) {

    NavHost(
        modifier = modifier,
        navController = mainTabNavigator.navController,
        startDestination = MissionRouteModel.Onboarding()
    ){
        onboardingNavGraph(
            onClickBoardSetup = { mainNavigator.navigationToBoardSetup() },
            onClickInvitationCode = { mainNavigator.navigationToInvitationCode() },
            onNavigateMissionBoard = { missionId ->
                mainTabNavigator.navigationToBoard(missionId)
            },
        )
        settingNavGraph(
            onBackClick = { mainNavigator.popBackStack() },
            onClickProfileSetting = { mainNavigator.navigateToProfileSetting() },
            onClickServicePolicy = { mainNavigator.navigationToServicePolicy() },
            onClickPrivacyPolicy = { mainNavigator.navigationToPrivacyPolicy() },
            onClickLogout = { mainNavigator.navigateToLogin() }
        )
        boardNavGraph(
            onNavigateOnboarding = {
                mainTabNavigator.navigationToOnboarding()
            },
            onNavigateDetail = { missionId ->
                mainNavigator.navigationToBoardDetail(missionId)
            },
            onNavigateFinish = { missionId ->
                mainNavigator.navigateToBoardFinish(missionId)
            },
            onNavigateStory = { userStory ->
                mainNavigator.navigationToUserStory(userStory)
            },
            onNavigateToPreview = { missionId, imageUrl ->
                mainNavigator.navigationToVerificationPreview(missionId, imageUrl)
            }
        )   
    } 
}
