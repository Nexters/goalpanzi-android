package com.goalpanzi.mission_mate.feature.board

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.goalpanzi.mission_mate.core.navigation.RouteModel.Mission
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
    this.navigate(Mission.Board(missionId), navOptions = navOptions)
}

fun NavGraphBuilder.boardNavGraph(
    onNavigateOnboarding: () -> Unit,
    onNavigateDetail : (Long) -> Unit,
    onNavigateFinish : (Long) -> Unit,
    onNavigateStory: (UserStory) -> Unit,
    onClickSetting: () -> Unit,
    onNavigateToPreview: (Long, Uri) -> Unit
) {
    composable<Mission.Board> { navBackStackEntry ->
        BoardRoute(
            onNavigateOnboarding = onNavigateOnboarding,
            onNavigateDetail = onNavigateDetail,
            onNavigateFinish = onNavigateFinish,
            onClickSetting = onClickSetting,
            onClickStory = onNavigateStory,
            onPreviewImage = onNavigateToPreview,
        )
    }
}

fun NavController.navigateToBoardDetail(
    missionId: Long
) {
    this.navigate(Mission.Detail(missionId))
}

fun NavGraphBuilder.boardDetailNavGraph(
    onNavigateOnboarding: () -> Unit,
    onBackClick: () -> Unit
) {
    composable<Mission.Detail> {
        BoardMissionDetailRoute(
            onNavigateOnboarding = onNavigateOnboarding,
            onBackClick = onBackClick
        )
    }
}

fun NavController.navigateToBoardFinish(
    missionId: Long
) {
    this.navigate(Mission.Finish(missionId))
}

fun NavGraphBuilder.boardFinishNavGraph(
    onClickSetting: () -> Unit,
    onClickOk: () -> Unit,
) {
    composable<Mission.Finish> {
        BoardFinishRoute(
            onSettingClick = onClickSetting,
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
            Mission.UserStory(
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
    composable<Mission.UserStory> { backStackEntry ->
        backStackEntry.toRoute<Mission.UserStory>().run {
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
    this.navigate(Mission.VerificationPreview(missionId,encodedUrl))
}

fun NavGraphBuilder.verificationPreviewNavGraph(
    onClickClose: () -> Unit,
    onUploadSuccess: () -> Unit
) {
    composable<Mission.VerificationPreview> {
        VerificationPreviewRoute(
            onClickClose = onClickClose,
            onUploadSuccess = { onUploadSuccess() }
        )
    }
}
