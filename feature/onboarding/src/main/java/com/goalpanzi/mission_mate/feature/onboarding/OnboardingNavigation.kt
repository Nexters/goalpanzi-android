package com.goalpanzi.mission_mate.feature.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.goalpanzi.mission_mate.core.navigation.RouteModel.OnboardingRouteModel
import com.goalpanzi.mission_mate.feature.onboarding.screen.OnboardingRoute
import com.goalpanzi.mission_mate.feature.onboarding.screen.boardsetup.BoardSetupRoute
import com.goalpanzi.mission_mate.feature.onboarding.screen.boardsetup.BoardSetupSuccessScreen
import com.goalpanzi.mission_mate.feature.onboarding.screen.invitation.InvitationCodeRoute

fun NavController.navigateToOnboarding(
    isAfterProfileCreate: Boolean = false,
    navOptions: NavOptions? = androidx.navigation.navOptions {
        popUpTo(this@navigateToOnboarding.graph.id) {
            inclusive = true
        }
    }
) {
    this.navigate(OnboardingRouteModel.Onboarding(isAfterProfileCreate), navOptions = navOptions)
}

fun NavGraphBuilder.onboardingNavGraph(
    onClickBoardSetup: () -> Unit,
    onClickInvitationCode: () -> Unit,
    onClickSetting: () -> Unit,
    onNavigateMissionBoard: (Long) -> Unit
) {
    composable<OnboardingRouteModel.Onboarding> {
        OnboardingRoute(
            onClickBoardSetup = onClickBoardSetup,
            onClickInvitationCode = onClickInvitationCode,
            onClickSetting = onClickSetting,
            onNavigateMissionBoard = onNavigateMissionBoard
        )
    }
}

fun NavController.navigateToBoardSetup() {
    this.navigate(OnboardingRouteModel.BoardSetup)
}

fun NavController.navigateToBoardSetupSuccess(
    navOptions: NavOptions? = androidx.navigation.navOptions {
        popUpTo(this@navigateToBoardSetupSuccess.graph.id) {
            inclusive = true
        }
    }
) {
    this.navigate(OnboardingRouteModel.BoardSetupSuccess, navOptions = navOptions)
}

fun NavController.navigateToInvitationCode() {
    this.navigate(OnboardingRouteModel.InvitationCode)
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
