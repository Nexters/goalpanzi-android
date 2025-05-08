package com.goalpanzi.mission_mate.feature.board.verification.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.goalpanzi.mission_mate.core.designsystem.component.StableImage
import com.goalpanzi.mission_mate.core.designsystem.ext.clickableWithoutRipple
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorBlack_FF000000
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.feature.board.model.CharacterUiModel
import com.goalpanzi.mission_mate.feature.board.model.toCharacterUiModel
import com.goalpanzi.mission_mate.feature.board.verification.model.VerificationUiState
import kotlinx.coroutines.flow.collectLatest
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun MyVerificationHistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: MyVerificationHistoryViewModel = hiltViewModel(),
    onClickClose: () -> Unit
) {
    val state by viewModel.verification.collectAsState()
    val initialPosition = remember { state.position }
    var isVisibleSpacer by remember { mutableStateOf(true) }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        HistoryHorizontalPager(
            modifier = modifier,
            initialPosition = initialPosition,
            items = state.items,
            onTouchEvent = { isVisibleSpacer = !isVisibleSpacer },
            onMoveNextPosition = { viewModel.onMoveNextPosition() },
            onMovePrevPosition = { viewModel.onMovePreviousPosition() }
        )
    }

    if (isVisibleSpacer) {
        VerificationHeader(
            modifier = modifier,
            characterUiModel = state.characterType?.toCharacterUiModel() ?: CharacterUiModel.RABBIT,
            nickname = state.nickname,
            verifiedAt = (state.items[state.position] as? VerificationUiState.VerificationItemState.Success)?.verifiedAt ?: "",
            onClickClose = onClickClose
        )
    }
}

@Composable
private fun HistoryHorizontalPager(
    modifier: Modifier = Modifier,
    initialPosition: Int,
    items: List<VerificationUiState.VerificationItemState>,
    onTouchEvent: () -> Unit = {},
    onMoveNextPosition: () -> Unit = {},
    onMovePrevPosition: () -> Unit = {}
) {
    val pagerState = rememberPagerState(initialPage = initialPosition) { items.size }

    LaunchedEffect(pagerState) {
        var prevPosition = pagerState.currentPage
        snapshotFlow { pagerState.currentPage }.collectLatest { currentPosition ->
            when {
                prevPosition < currentPosition -> onMoveNextPosition()
                prevPosition > currentPosition -> onMovePrevPosition()
                else -> Unit
            }
            prevPosition = currentPosition
        }
    }

    HorizontalPager(
        modifier = modifier.fillMaxSize(),
        state = pagerState
    ) { position ->
        when (items[position]) {
            is VerificationUiState.VerificationItemState.Loading -> {
                LoadingScreen(modifier = modifier)
            }
            is VerificationUiState.VerificationItemState.Success -> {
                StoryImageViewer(
                    imageUrl = (items[position] as VerificationUiState.VerificationItemState.Success).image,
                    onTouchEvent = { onTouchEvent() }
                )
            }
        }
    }
}

@Composable
private fun StoryImageViewer(
    imageUrl: String,
    onTouchEvent: () -> Unit = {}
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(URLDecoder.decode(imageUrl, StandardCharsets.UTF_8.toString()))
            .build(),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .clickableWithoutRipple {
                onTouchEvent()
            },
        contentScale = ContentScale.Fit,
        filterQuality = FilterQuality.None
    )
}

@Composable
private fun VerificationHeader(
    modifier: Modifier = Modifier,
    characterUiModel: CharacterUiModel,
    nickname: String,
    verifiedAt: String,
    onClickClose: () -> Unit
) {
    val statusBarPaddingValue = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(statusBarPaddingValue + 80.dp)
            .background(ColorBlack_FF000000.copy(alpha = 0.7f))
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(93.dp)
            .padding(horizontal = 24.dp, vertical = 14.dp)
    ) {
        StableImage(
            modifier = modifier
                .padding(top = 6.dp)
                .size(28.dp)
                .border(1.dp, ColorWhite_FFFFFFFF, CircleShape)
                .paint(
                    painter = painterResource(characterUiModel.backgroundId),
                    contentScale = ContentScale.FillWidth
                )
                .padding(5.dp),
            drawableResId = characterUiModel.imageId,
            description = ""
        )
        Text(
            text = nickname,
            style = MissionMateTypography.body_xl_bold,
            color = ColorWhite_FFFFFFFF,
            modifier = modifier
                .padding(start = 8.dp)
                .wrapContentWidth()
                .padding(top = 6.dp)
        )

        Text(
            text = verifiedAt,
            style = MissionMateTypography.body_xl_regular,
            color = ColorWhite_FFFFFFFF,
            modifier = modifier
                .padding(start = 8.dp)
                .weight(1f)
                .padding(top = 6.dp)
        )

        IconButton(
            onClick = {
                onClickClose()
            },
            modifier = modifier.wrapContentSize()
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = com.goalpanzi.mission_mate.core.designsystem.R.drawable.ic_close),
                contentDescription = "",
                tint = ColorWhite_FFFFFFFF
            )
        }
    }
}

@Composable
private fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .focusable()
            .clickable {}
    ) {
        CircularProgressIndicator(
            modifier = modifier.align(Alignment.Center)
        )
    }
}