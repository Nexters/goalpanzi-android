package com.goalpanzi.mission_mate.feature.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.goalpanzi.mission_mate.core.navigation.OnboardingRouteModel
import com.goalpanzi.mission_mate.core.navigation.RouteModel
import com.goalpanzi.mission_mate.feature.onboarding.screen.OnboardingRoute
import com.goalpanzi.mission_mate.feature.onboarding.screen.boardsetup.BoardSetupRoute
import com.goalpanzi.mission_mate.feature.onboarding.screen.boardsetup.BoardSetupSuccessScreen
import com.goalpanzi.mission_mate.feature.onboarding.screen.invitation.InvitationCodeRoute

fun NavController.navigateToOnboarding() {
    this.navigate(RouteModel.Onboarding)
}

fun NavController.navigateToBoardSetup() {
    this.navigate(OnboardingRouteModel.BoardSetup)
}

fun NavController.navigateToBoardSetupSuccess() {
    this.navigate(OnboardingRouteModel.BoardSetupSuccess)
}

fun NavController.navigateToInvitationCode() {
    this.navigate(OnboardingRouteModel.InvitationCode)
}

fun NavGraphBuilder.onboardingNavGraph(
    onClickBoardSetup: () -> Unit,
    onClickInvitationCode: () -> Unit,
    onClickSetting: () -> Unit
) {
    composable<RouteModel.Onboarding> {
        OnboardingRoute(
            onClickBoardSetup = onClickBoardSetup,
            onClickInvitationCode = onClickInvitationCode,
            onClickSetting = onClickSetting
        )
    }
}

fun NavGraphBuilder.boardSetupNavGraph(
    onSuccess: () -> Unit,
    onBackClick: () -> Unit
) {
    composable<OnboardingRouteModel.BoardSetup> {
        BoardSetupRoute(
            onSuccess = onSuccess,
            onBackClick = onBackClick
        )
    }
}

fun NavGraphBuilder.boardSetupSuccessNavGraph(
    onClickStart: () -> Unit
) {
    composable<OnboardingRouteModel.BoardSetupSuccess> {
        BoardSetupSuccessScreen(
            onClickStart = onClickStart
        )
    }
}

fun NavGraphBuilder.invitationCodeNavGraph(
    onBackClick: () -> Unit,
    onNavigateMissionBoard: (Long) -> Unit,
) {
    composable<OnboardingRouteModel.InvitationCode> {
        InvitationCodeRoute(
            onBackClick = onBackClick,
            onNavigateMissionBoard = onNavigateMissionBoard
        )
    }
}