package com.goalpanzi.mission_mate.core.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.goalpanzi.mission_mate.core.navigation.RouteModel
import com.goalpanzi.mission_mate.feature.board.boardNavGraph
import com.goalpanzi.mission_mate.feature.login.loginNavGraph
import com.goalpanzi.mission_mate.feature.onboarding.boardSetupNavGraph
import com.goalpanzi.mission_mate.feature.onboarding.boardSetupSuccessNavGraph
import com.goalpanzi.mission_mate.feature.onboarding.invitationCodeNavGraph
import com.goalpanzi.mission_mate.feature.onboarding.onboardingNavGraph
import com.luckyoct.feature.profile.profileNavGraph
import com.luckyoct.feature.setting.navigation.inquiryNavGraph
import com.luckyoct.feature.setting.navigation.privacyPolicyNavGraph
import com.luckyoct.feature.setting.navigation.servicePolicyNavGraph
import com.luckyoct.feature.setting.navigation.settingNavGraph

@Composable
internal fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    startDestination: RouteModel,
    padding: PaddingValues
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceDim)
    ) {
        NavHost(
            navController = navigator.navController,
            startDestination = startDestination
        ) {
            loginNavGraph(
                onLoginSuccess = { if (it) navigator.navigationToOnboarding() else navigator.navigateToProfileCreate() }
            )
            onboardingNavGraph(
                onClickBoardSetup = { navigator.navigationToBoardSetup() },
                onClickInvitationCode = { navigator.navigationToInvitationCode() },
                onNavigateMissionBoard = { missionId ->
                    navigator.navigationToBoard(missionId)
                },
                onClickSetting = { navigator.navigationToSetting() }
            )
            boardSetupNavGraph(
                onSuccess = {
                    navigator.navigationToBoardSetupSuccess()
                },
                onBackClick = {
                    navigator.popBackStack()
                }
            )
            boardSetupSuccessNavGraph(
                onClickStart = {
                    navigator.navigationToOnboarding()
                }
            )
            invitationCodeNavGraph(
                onBackClick = {
                    navigator.popBackStack()
                },
                onNavigateMissionBoard = { missionId ->
                    navigator.navigationToBoard(missionId)
                }
            )
            profileNavGraph(
                onSaveSuccess = { navigator.navigationToOnboarding() },
                onBackClick = { navigator.popBackStack() }
            )
            settingNavGraph(
                onBackClick = { navigator.popBackStack() },
                onClickProfileSetting = { navigator.navigateToProfileSetting() },
                onClickServicePolicy = { navigator.navigationToServicePolicy() },
                onClickPrivacyPolicy = { navigator.navigationToPrivacyPolicy() },
                onClickLogout = { navigator.navigateToLogin() }
            )
            inquiryNavGraph(
                onBackClick = { navigator.popBackStack() }
            )
            servicePolicyNavGraph(
                onBackClick = { navigator.popBackStack() }
            )
            privacyPolicyNavGraph(
                onBackClick = { navigator.popBackStack() }
            )
            boardNavGraph(
                onNavigateOnboarding = {
                    navigator.navigationToOnboarding()
                }
            )
        }
    }
}