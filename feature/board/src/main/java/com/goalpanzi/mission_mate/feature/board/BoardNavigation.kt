package com.goalpanzi.mission_mate.feature.board

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.goalpanzi.mission_mate.core.navigation.RouteModel
import com.goalpanzi.mission_mate.feature.board.screen.BoardRoute
import com.goalpanzi.mission_mate.feature.onboarding.navigateToOnboarding


fun NavController.navigateToBoard(
    missionId: Long,
    navOptions: NavOptions? = androidx.navigation.navOptions {
        popUpTo(this@navigateToBoard.graph.id){
            inclusive = true
        }
    }
) {
    this.navigate(RouteModel.Board(missionId = missionId),navOptions = navOptions)
}

fun NavGraphBuilder.boardNavGraph(
    onNavigateOnboarding: () -> Unit,
    onClickSetting : () -> Unit
) {
    composable<RouteModel.Board> { navBackStackEntry ->
        val missionId = navBackStackEntry.toRoute<RouteModel.Board>().missionId
        BoardRoute(
            missionId = missionId,
            onNavigateOnboarding = onNavigateOnboarding,
            onClickSetting = onClickSetting
        )
    }
}