package com.goalpanzi.mission_mate.feature.board

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.goalpanzi.mission_mate.core.navigation.model.RouteModel.MainTabRoute.MissionRouteModel
import com.goalpanzi.mission_mate.core.ui.util.slideInFromLeft
import com.goalpanzi.mission_mate.core.ui.util.slideInToUp
import com.goalpanzi.mission_mate.core.ui.util.slideOutToDown
import com.goalpanzi.mission_mate.core.ui.util.slideOutToEnd
import com.goalpanzi.mission_mate.feature.board.model.CharacterUiModel
import com.goalpanzi.mission_mate.feature.board.model.UserStory
import com.goalpanzi.mission_mate.feature.board.screen.BoardFinishRoute
import com.goalpanzi.mission_mate.feature.board.screen.BoardMissionDetailRoute
import com.goalpanzi.mission_mate.feature.board.screen.BoardRoute
import com.goalpanzi.mission_mate.feature.board.screen.UserStoryScreen
import com.goalpanzi.mission_mate.feature.board.screen.VerificationPreviewRoute
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
    onNavigateToPreview: (Long, Uri) -> Unit
) {
    composable<MissionRouteModel.Board> { navBackStackEntry ->
        BoardRoute(
            onNavigateOnboarding = onNavigateOnboarding,
            onNavigateDetail = onNavigateDetail,
            onNavigateFinish = onNavigateFinish,
            onClickStory = onNavigateStory,
            onPreviewImage = onNavigateToPreview,
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
            slideInFromLeft()
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
    userStory: UserStory
) = with(userStory) {
    val encodedUrl = URLEncoder.encode(imageUrl, StandardCharsets.UTF_8.toString())
    this@navigateToUserStory
        .navigate(
            MissionRouteModel.UserStory(
                userCharacter = characterUiModelType.name.uppercase(),
                nickname = nickname,
                verifiedAt = verifiedAt,
                imageUrl = encodedUrl
            )
        )
}

fun NavGraphBuilder.userStoryNavGraph(
    onClickClose: () -> Unit
) {
    composable<MissionRouteModel.UserStory>(
        enterTransition = {
            slideInToUp()
        },
        exitTransition = {
            slideOutToDown()
        }
    ) { backStackEntry ->
        backStackEntry.toRoute<MissionRouteModel.UserStory>().run {
            val characterUiModel = userCharacter.let { CharacterUiModel.valueOf(it) }
            UserStoryScreen(
                characterUiModel = characterUiModel,
                nickname = nickname,
                verifiedAt = verifiedAt,
                imageUrl = imageUrl,
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
            slideInToUp()
        },
        exitTransition = {
            slideOutToDown()
        }
    ) {
        VerificationPreviewRoute(
            onClickClose = onClickClose,
            onUploadSuccess = { onUploadSuccess() }
        )
    }
}
