package com.goalpanzi.mission_mate.feature.onboarding

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.goalpanzi.mission_mate.core.navigation.RouteModel.MainTabRoute.MissionRouteModel
import com.goalpanzi.mission_mate.core.ui.util.slideInFromLeft
import com.goalpanzi.mission_mate.core.ui.util.slideOutToEnd
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
    this.navigate(MissionRouteModel.Onboarding(isAfterProfileCreate), navOptions = navOptions)
}

fun NavGraphBuilder.onboardingNavGraph(
    onClickBoardSetup: () -> Unit,
    onClickInvitationCode: () -> Unit,
    onClickSetting: () -> Unit,
    onNavigateMissionBoard: (Long) -> Unit
) {
    composable<MissionRouteModel.Onboarding> {
        OnboardingRoute(
            onClickBoardSetup = onClickBoardSetup,
            onClickInvitationCode = onClickInvitationCode,
            onClickSetting = onClickSetting,
            onNavigateMissionBoard = onNavigateMissionBoard
        )
    }
}

fun NavController.navigateToBoardSetup() {
    this.navigate(MissionRouteModel.BoardSetup)
}

fun NavController.navigateToBoardSetupSuccess(
    navOptions: NavOptions? = androidx.navigation.navOptions {
        popUpTo(this@navigateToBoardSetupSuccess.graph.id) {
            inclusive = true
        }
    }
) {
    this.navigate(MissionRouteModel.BoardSetupSuccess, navOptions = navOptions)
}

fun NavController.navigateToInvitationCode() {
    this.navigate(MissionRouteModel.InvitationCode)
}

fun NavGraphBuilder.boardSetupNavGraph(
    onSuccess: () -> Unit,
    onBackClick: () -> Unit
) {
    composable<MissionRouteModel.BoardSetup>(
        enterTransition = {
            slideInFromLeft()
        },
        popExitTransition = {
            slideOutToEnd()
        }
    ) {
        BoardSetupRoute(
            onSuccess = onSuccess,
            onBackClick = onBackClick
        )
    }
}

fun NavGraphBuilder.boardSetupSuccessNavGraph(
    onClickStart: () -> Unit
) {
    composable<MissionRouteModel.BoardSetupSuccess> {
        BoardSetupSuccessScreen(
            onClickStart = onClickStart
        )
    }
}

fun NavGraphBuilder.invitationCodeNavGraph(
    onBackClick: () -> Unit,
    onNavigateMissionBoard: (Long) -> Unit,
) {
    composable<MissionRouteModel.InvitationCode>(
        enterTransition = {
            slideInFromLeft()
        },
        popExitTransition = {
            slideOutToEnd()
        }
    ) {
        InvitationCodeRoute(
            onBackClick = onBackClick,
            onNavigateMissionBoard = onNavigateMissionBoard
        )
    }
}
