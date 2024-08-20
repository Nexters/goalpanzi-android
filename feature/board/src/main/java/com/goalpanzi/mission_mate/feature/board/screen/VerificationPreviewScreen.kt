package com.goalpanzi.mission_mate.feature.board.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorBlack_FF000000
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun VerificationPreviewRoute(
    onClickClose: () -> Unit,
    viewModel: VerificationPreviewViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    VerificationPreviewScreen(
        onClickClose = onClickClose,
        uiState = uiState
    )
}

@Composable
fun VerificationPreviewScreen(
    onClickClose: () -> Unit,
    uiState: VerificationPreviewUiState
) {
    val dateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorWhite_FFFFFFFF)
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            when (uiState) {
                VerificationPreviewUiState.Loading -> VerificationPreviewLoading()
                is VerificationPreviewUiState.Success -> {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(URLDecoder.decode(uiState.imageUrl, StandardCharsets.UTF_8.toString()))
                            .build(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds,
                        filterQuality = FilterQuality.None
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        ColorBlack_FF000000.copy(alpha = 0.4f),
                                        Color.Transparent
                                    )
                                )
                            )
                            .height(93.dp)
                            .padding(horizontal = 24.dp, vertical = 14.dp)
                    ) {
                        Image(
                            modifier = Modifier
                                .padding(top = 6.dp)
                                .size(28.dp)
                                .border(1.dp, ColorWhite_FFFFFFFF, CircleShape)
                                .paint(
                                    painter = painterResource(uiState.character.backgroundId),
                                    contentScale = ContentScale.FillWidth
                                )
                                .padding(5.dp),
                            painter = painterResource(uiState.character.imageId),
                            contentDescription = ""
                        )
                        Text(
                            text = uiState.nickname,
                            style = MissionMateTypography.body_xl_bold,
                            color = ColorWhite_FFFFFFFF,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .wrapContentWidth()
                                .padding(top = 6.dp)
                        )

                        Text(
                            text = dateTime.format(formatter),
                            style = MissionMateTypography.body_xl_regular,
                            color = ColorWhite_FFFFFFFF,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .weight(1f)
                                .padding(top = 6.dp)
                        )

                        IconButton(
                            onClick = onClickClose,
                            modifier = Modifier.wrapContentSize()
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = com.goalpanzi.mission_mate.core.designsystem.R.drawable.ic_close),
                                contentDescription = "",
                                tint = ColorWhite_FFFFFFFF
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VerificationPreviewLoading() {
    Box(
        modifier = Modifier
            .background(ColorWhite_FFFFFFFF)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}