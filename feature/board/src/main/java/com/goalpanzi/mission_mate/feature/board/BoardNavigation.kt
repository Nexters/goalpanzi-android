package com.goalpanzi.mission_mate.feature.board

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.goalpanzi.mission_mate.core.navigation.RouteModel
import com.goalpanzi.mission_mate.feature.board.screen.BoardRoute
import com.goalpanzi.mission_mate.feature.onboarding.navigateToOnboarding

internal const val missionIdArg = "missionId"


fun NavController.navigateToBoard(
    missionId: Long,
    navOptions: NavOptions? = androidx.navigation.navOptions {
        popUpTo(this@navigateToBoard.graph.id){
            inclusive = true
        }
    }
) {
    this.navigate("RouteModel.Board" + "/${missionId}",navOptions = navOptions)
}

fun NavGraphBuilder.boardNavGraph(
    onNavigateOnboarding: () -> Unit,
    onClickSetting : () -> Unit
) {
    composable(
        "RouteModel.Board/{$missionIdArg}",
        arguments = listOf(navArgument(missionIdArg) { type = NavType.LongType })
    ) { navBackStackEntry ->
      //  val missionId = navBackStackEntry.toRoute<RouteModel.Board>().missionId
        BoardRoute(
            onNavigateOnboarding = onNavigateOnboarding,
            onClickSetting = onClickSetting
        )
    }
}