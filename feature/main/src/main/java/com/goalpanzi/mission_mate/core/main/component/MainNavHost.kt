package com.goalpanzi.mission_mate.core.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.feature.board.boardDetailNavGraph
import com.goalpanzi.mission_mate.feature.board.boardFinishNavGraph
import com.goalpanzi.mission_mate.feature.board.boardNavGraph
import com.goalpanzi.mission_mate.feature.board.userStoryNavGraph
import com.goalpanzi.mission_mate.feature.board.verificationPreviewNavGraph
import com.goalpanzi.mission_mate.feature.login.loginNavGraph
import com.goalpanzi.mission_mate.feature.onboarding.boardSetupNavGraph
import com.goalpanzi.mission_mate.feature.onboarding.boardSetupSuccessNavGraph
import com.goalpanzi.mission_mate.feature.onboarding.invitationCodeNavGraph
import com.goalpanzi.mission_mate.feature.onboarding.onboardingNavGraph
import com.luckyoct.feature.profile.profileNavGraph
import com.luckyoct.feature.setting.navigation.privacyPolicyNavGraph
import com.luckyoct.feature.setting.navigation.servicePolicyNavGraph
import com.luckyoct.feature.setting.navigation.settingNavGraph

@Composable
internal fun MainNavHost(
    modifier: Modifier = Modifier,
    navigator: MainNavigator,
    startDestination: String,
    padding: PaddingValues
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ColorWhite_FFFFFFFF)
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
            servicePolicyNavGraph(
                onBackClick = { navigator.popBackStack() }
            )
            privacyPolicyNavGraph(
                onBackClick = { navigator.popBackStack() }
            )
            boardNavGraph(
                onNavigateOnboarding = {
                    navigator.navigationToOnboarding()
                },
                onNavigateDetail = { missionId ->
                    navigator.navigationToBoardDetail(missionId)
                },
                onNavigateFinish = { missionId ->
                    navigator.navigateToBoardFinish(missionId)
                },
                onClickSetting = {
                    navigator.navigationToSetting()
                },
                onNavigateStory = { userStory ->
                    navigator.navigationToUserStory(userStory)
                },
                onNavigateToPreview = { missionId, imageUrl ->
                    navigator.navigationToVerificationPreview(missionId, imageUrl)
                }
            )
            boardDetailNavGraph(
                onDelete = {
                    navigator.navigationToOnboarding()
                },
                onBackClick = {
                    navigator.popBackStack()
                }
            )
            boardFinishNavGraph(
                onClickSetting = {
                    navigator.navigationToSetting()
                },
                onClickOk = {
                    navigator.navigationToOnboarding()
                }
            )
            userStoryNavGraph(
                onClickClose = {
                    navigator.popBackStack()
                }
            )
            verificationPreviewNavGraph(
                onClickClose = {
                    navigator.popBackStack()
                },
                onUploadSuccess = {
                    navigator.popBackStack()
                    navigator.navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set(it, true)
                }
            )
        }
    }
}