package com.goalpanzi.mission_mate.feature.board

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.goalpanzi.mission_mate.feature.board.screen.BoardMissionDetailRoute
import com.goalpanzi.mission_mate.feature.board.screen.BoardRoute

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
    onNavigateDetail : (Long) -> Unit,
    onClickSetting : () -> Unit
) {
    composable(
        "RouteModel.Board/{$missionIdArg}",
        arguments = listOf(navArgument(missionIdArg) { type = NavType.LongType })
    ) { navBackStackEntry ->
        val missionId = navBackStackEntry.arguments?.getLong(missionIdArg)
        BoardRoute(
            onNavigateOnboarding = onNavigateOnboarding,
            onNavigateDetail = {
                missionId?.let {
                    onNavigateDetail(missionId)
                }
            },
            onClickSetting = onClickSetting
        )
    }
}

fun NavController.navigateToBoardDetail(
    missionId: Long
) {
    this.navigate("RouteModel.BoardDetail" + "/${missionId}")
}

fun NavGraphBuilder.boardDetailNavGraph(
    onDelete: () -> Unit,
    onBackClick : () -> Unit
) {
    composable(
        "RouteModel.BoardDetail/{$missionIdArg}",
        arguments = listOf(navArgument(missionIdArg) { type = NavType.LongType })
    ) {
        BoardMissionDetailRoute(
            onDelete = onDelete,
            onBackClick = onBackClick
        )
    }
}