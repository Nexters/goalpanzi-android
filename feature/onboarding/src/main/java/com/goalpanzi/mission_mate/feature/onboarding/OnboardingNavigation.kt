package com.goalpanzi.mission_mate.feature.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.goalpanzi.mission_mate.core.navigation.OnboardingRouteModel
import com.goalpanzi.mission_mate.core.navigation.RouteModel

fun NavController.navigateToOnboarding() {
    this.navigate(RouteModel.Onboarding)
}

fun NavController.navigateToBoardSetup() {
    this.navigate(OnboardingRouteModel.BoardSetup)
}

fun NavController.navigateToInvitationCode() {
    this.navigate(OnboardingRouteModel.InvitationCode)
}

fun NavGraphBuilder.onboardingNavGraph(
    onClickBoardSetup : () -> Unit,
    onClickInvitationCode : () -> Unit,
    onClickSetting : () -> Unit
) {
    composable<RouteModel.Onboarding> {
        OnboardingRoute(
            onClickBoardSetup = onClickBoardSetup,
            onClickInvitationCode = onClickInvitationCode,
            onClickSetting = onClickSetting
        )
    }
}

fun NavGraphBuilder.boardSetupNavGraph() {
    composable<OnboardingRouteModel.BoardSetup> {

    }
}

fun NavGraphBuilder.invitationCodeNavGraph() {
    composable<OnboardingRouteModel.InvitationCode> {

    }
}