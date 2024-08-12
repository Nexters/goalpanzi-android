package com.goalpanzi.mission_mate.feature.board

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.goalpanzi.mission_mate.core.navigation.RouteModel
import com.goalpanzi.mission_mate.feature.board.screen.BoardRoute


fun NavController.navigateToBoard() {
    this.navigate(RouteModel.Board)
}

fun NavGraphBuilder.boardNavGraph(
    onNavigateOnboarding: () -> Unit
) {
    composable<RouteModel.Board> {
        BoardRoute(
            onNavigateOnboarding = onNavigateOnboarding
        )
    }
}