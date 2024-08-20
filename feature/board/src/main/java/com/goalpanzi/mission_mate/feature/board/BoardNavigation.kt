package com.goalpanzi.mission_mate.feature.board

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.goalpanzi.mission_mate.feature.board.model.Character
import com.goalpanzi.mission_mate.feature.board.model.UserStory
import com.goalpanzi.mission_mate.feature.board.screen.BoardFinishRoute
import com.goalpanzi.mission_mate.feature.board.screen.BoardMissionDetailRoute
import com.goalpanzi.mission_mate.feature.board.screen.BoardRoute
import com.goalpanzi.mission_mate.feature.board.screen.UserStoryScreen
import com.goalpanzi.mission_mate.feature.board.screen.VerificationPreviewRoute
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

internal const val missionIdArg = "missionId"
internal const val userCharacterTypeArg = "userCharacterType"
internal const val nicknameArg = "nickname"
internal const val dateArg = "date"
internal const val imageUrlArg = "imageUrl"
internal const val isUploadSuccessArg = "isUploadSuccess"

fun NavController.navigateToBoard(
    missionId: Long,
    navOptions: NavOptions? = androidx.navigation.navOptions {
        popUpTo(this@navigateToBoard.graph.id) {
            inclusive = true
        }
    }
) {
    this.navigate("RouteModel.Board" + "/${missionId}", navOptions = navOptions)
}

fun NavGraphBuilder.boardNavGraph(
    onNavigateOnboarding: () -> Unit,
    onNavigateDetail : (Long) -> Unit,
    onNavigateFinish : (Long) -> Unit,
    onNavigateStory: (UserStory) -> Unit,
    onClickSetting: () -> Unit,
    onNavigateToPreview: (Long, Uri) -> Unit
) {
    composable(
        "RouteModel.Board/{$missionIdArg}",
        arguments = listOf(navArgument(missionIdArg) { type = NavType.LongType })
    ) { navBackStackEntry ->
        val missionId = navBackStackEntry.arguments?.getLong(missionIdArg)
        val isUploadSuccess = navBackStackEntry.savedStateHandle.get<Boolean>(isUploadSuccessArg)
        BoardRoute(
            onNavigateOnboarding = onNavigateOnboarding,
            onNavigateDetail = {
                missionId?.let {
                    onNavigateDetail(missionId)
                }
            },
            onNavigateFinish = onNavigateFinish,
            onClickSetting = onClickSetting,
            onClickStory = onNavigateStory,
            onPreviewImage = onNavigateToPreview,
            isUploadSuccess = isUploadSuccess ?: false
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
    onBackClick: () -> Unit
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

fun NavController.navigateToBoardFinish(
    missionId: Long
) {
    this.navigate("RouteModel.BoardFinish" + "/${missionId}")
}

fun NavGraphBuilder.boardFinishNavGraph(
    onClickSetting: () -> Unit,
    onClickOk: () -> Unit,
) {
    composable(
        "RouteModel.BoardFinish/{$missionIdArg}",
        arguments = listOf(navArgument(missionIdArg) { type = NavType.LongType })
    ) {
        BoardFinishRoute(
            onClickSetting = onClickSetting,
            onClickOk = onClickOk
        )
    }
}

fun NavController.navigateToUserStory(
    userStory: UserStory
) = with(userStory) {
    val encodedUrl = URLEncoder.encode(imageUrl, StandardCharsets.UTF_8.toString())
    this@navigateToUserStory
        .navigate(
            route = "RouteModel.UserStory" + "/${characterType.name.uppercase()}" + "/${nickname}" + "/${verifiedAt}" + "/${encodedUrl}"
        )
}

fun NavGraphBuilder.userStoryNavGraph(
    onClickClose: () -> Unit
) {
    composable(
        route = "RouteModel.UserStory/{$userCharacterTypeArg}/{$nicknameArg}/{$dateArg}/{$imageUrlArg}",
        arguments = listOf(
            navArgument(userCharacterTypeArg) {
                defaultValue = Character.RABBIT.name.uppercase()
                type = NavType.StringType
            },
            navArgument(nicknameArg) {
                type = NavType.StringType
            },
            navArgument(dateArg) {
                type = NavType.StringType
            },
            navArgument(imageUrlArg) {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        backStackEntry.arguments?.run {
            val character = getString(userCharacterTypeArg)?.let { Character.valueOf(it) }
                ?: Character.RABBIT
            val nickname = getString(nicknameArg) ?: ""
            val verifiedAt = getString(dateArg) ?: ""
            val imageUrl = getString(imageUrlArg) ?: ""

            UserStoryScreen(
                character = character,
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
    this.navigate("RouteModel.VerificationPreview" + "/${missionId}" +"/${encodedUrl}")
}

fun NavGraphBuilder.verificationPreviewNavGraph(
    onClickClose: () -> Unit,
    onUploadSuccess: (key: String) -> Unit
) {
    composable(
        route = "RouteModel.VerificationPreview/{$missionIdArg}/{$imageUrlArg}",
        arguments = listOf(
            navArgument(missionIdArg) {
              type = NavType.LongType
            },
            navArgument(imageUrlArg) {
                type = NavType.StringType
            }
        )
    ) {
        VerificationPreviewRoute(
            onClickClose = onClickClose,
            onUploadSuccess = { onUploadSuccess(isUploadSuccessArg) }
        )
    }
}
