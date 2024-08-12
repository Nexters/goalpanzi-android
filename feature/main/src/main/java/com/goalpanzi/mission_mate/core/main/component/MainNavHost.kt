package com.goalpanzi.mission_mate.core.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.goalpanzi.mission_mate.feature.login.loginNavGraph
import com.goalpanzi.mission_mate.feature.onboarding.boardSetupNavGraph
import com.goalpanzi.mission_mate.feature.onboarding.boardSetupSuccessNavGraph
import com.goalpanzi.mission_mate.feature.onboarding.invitationCodeNavGraph
import com.goalpanzi.mission_mate.feature.onboarding.onboardingNavGraph
import com.luckyoct.feature.profile.ProfileSettingType
import com.luckyoct.feature.profile.profileNavGraph

@Composable
internal fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    padding: PaddingValues
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceDim)
    ) {
        NavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination
        ) {
            loginNavGraph(
                onLoginSuccess = { if (it) navigator.navigationToOnboarding() else navigator.navigateToProfileCreate() }
            )
            onboardingNavGraph(
                onClickBoardSetup = { navigator.navigationToBoardSetup() },
                onClickInvitationCode = { navigator.navigationToInvitationCode() },
                onClickSetting = {  }
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

                }
            )
            invitationCodeNavGraph(
                onBackClick = {
                    navigator.popBackStack()
                },
                onNavigateMissionBoard = { missionId ->

                }
            )
            profileNavGraph(
                onSaveSuccess = { navigator.navigationToOnboarding() }
            )
        }
    }
}