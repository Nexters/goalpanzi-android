package com.goalpanzi.mission_mate.feature.board

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.goalpanzi.mission_mate.core.navigation.model.RouteModel.MainTabRoute.MissionRouteModel
import com.goalpanzi.mission_mate.core.ui.util.slideInFromBottom
import com.goalpanzi.mission_mate.core.ui.util.slideInFromEnd
import com.goalpanzi.mission_mate.core.ui.util.slideOutToBottom
import com.goalpanzi.mission_mate.core.ui.util.slideOutToEnd
import com.goalpanzi.mission_mate.feature.board.model.CharacterUiModel
import com.goalpanzi.mission_mate.feature.board.verification.model.MyVerificationExtra
import com.goalpanzi.mission_mate.feature.board.model.UserStory
import com.goalpanzi.mission_mate.feature.board.screen.BoardFinishRoute
import com.goalpanzi.mission_mate.feature.board.screen.BoardMissionDetailRoute
import com.goalpanzi.mission_mate.feature.board.screen.BoardRoute
import com.goalpanzi.mission_mate.feature.board.screen.UserStoryScreen
import com.goalpanzi.mission_mate.feature.board.verification.screen.MyVerificationHistoryScreen
import com.goalpanzi.mission_mate.feature.board.verification.screen.VerificationPreviewRoute
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun NavController.navigateToBoard(
    missionId: Long,
    navOptions: NavOptions? = androidx.navigation.navOptions {
        popUpTo(this@navigateToBoard.graph.id) {
            inclusive = true
        }
    }
) {
    this.navigate(MissionRouteModel.Board(missionId), navOptions = navOptions)
}

fun NavGraphBuilder.boardNavGraph(
    onNavigateOnboarding: () -> Unit,
    onNavigateDetail : (Long) -> Unit,
    onNavigateFinish : (Long) -> Unit,
    onNavigateStory: (UserStory) -> Unit,
    onNavigateToPreview: (Long, Uri) -> Unit,
    onNavigateMyVerificationHistory: (MyVerificationExtra) -> Unit
) {
    composable<MissionRouteModel.Board> { navBackStackEntry ->
        BoardRoute(
            onNavigateOnboarding = onNavigateOnboarding,
            onNavigateDetail = onNavigateDetail,
            onNavigateFinish = onNavigateFinish,
            onClickStory = onNavigateStory,
            onPreviewImage = onNavigateToPreview,
            onNavigateMyVerificationHistory = onNavigateMyVerificationHistory
        )
    }
}

fun NavController.navigateToBoardDetail(
    missionId: Long
) {
    this.navigate(MissionRouteModel.Detail(missionId))
}

fun NavGraphBuilder.boardDetailNavGraph(
    onNavigateOnboarding: () -> Unit,
    onBackClick: () -> Unit
) {
    composable<MissionRouteModel.Detail>(
        enterTransition = {
            slideInFromEnd()
        },
        popExitTransition = {
            slideOutToEnd()
        }
    ) {
        BoardMissionDetailRoute(
            onNavigateOnboarding = onNavigateOnboarding,
            onBackClick = onBackClick
        )
    }
}

fun NavController.navigateToBoardFinish(
    missionId: Long
) {
    this.navigate(MissionRouteModel.Finish(missionId))
}

fun NavGraphBuilder.boardFinishNavGraph(
    onClickOk: () -> Unit,
) {
    composable<MissionRouteModel.Finish> {
        BoardFinishRoute(
            onOkClick = onClickOk
        )
    }
}

fun NavController.navigateToUserStory(
    character: CharacterUiModel
) {
    this@navigateToUserStory
        .navigate(
            MissionRouteModel.UserStory(
                userCharacter = character.name.uppercase()
            )
        )
}

fun NavGraphBuilder.userStoryNavGraph(
    onClickClose: () -> Unit
) {
    composable<MissionRouteModel.UserStory>(
        enterTransition = {
            slideInFromBottom()
        },
        exitTransition = {
            slideOutToBottom()
        }
    ) { backStackEntry ->
        backStackEntry.toRoute<MissionRouteModel.UserStory>().run {
            val characterUiModel = userCharacter.let { CharacterUiModel.valueOf(it) }
            UserStoryScreen(
                characterUiModel = characterUiModel,
                onClickClose = onClickClose
            )
        }
    }
}

fun NavController.navigateToVerificationPreview(
    missionId: Long,
    imageUrl: Uri
) {
    val encodedUrl = URLEncoder.encode(imageUrl.toString(), StandardCharsets.UTF_8.toString())
    this.navigate(MissionRouteModel.VerificationPreview(missionId,encodedUrl))
}

fun NavGraphBuilder.verificationPreviewNavGraph(
    onClickClose: () -> Unit,
    onUploadSuccess: () -> Unit
) {
    composable<MissionRouteModel.VerificationPreview>(
        enterTransition = {
            slideInFromBottom()
        },
        exitTransition = {
            slideOutToBottom()
        }
    ) {
        VerificationPreviewRoute(
            onClickClose = onClickClose,
            onUploadSuccess = { onUploadSuccess() }
        )
    }
}

fun NavController.navigateToMyVerificationHistory(extra: MyVerificationExtra) {
    this.navigate(extra.toRouteModel())
}

fun NavGraphBuilder.myVerificationHistoryNavGraph(
    onClickClose: () -> Unit
) {
    composable<MissionRouteModel.MyVerificationHistory> {
        MyVerificationHistoryScreen(
            onClickClose = onClickClose
        )
    }
}
