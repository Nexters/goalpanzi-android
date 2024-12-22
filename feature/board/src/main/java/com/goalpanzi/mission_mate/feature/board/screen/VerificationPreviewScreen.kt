package com.goalpanzi.mission_mate.feature.board.screen

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateButton
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateButtonType
import com.goalpanzi.mission_mate.core.designsystem.component.StableImage
import com.goalpanzi.mission_mate.core.designsystem.ext.clickableWithoutRipple
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorBlack_FF000000
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.core.designsystem.util.MultipleEventsCutter
import com.goalpanzi.mission_mate.core.designsystem.util.get
import com.goalpanzi.mission_mate.feature.board.R
import com.goalpanzi.mission_mate.feature.board.model.CharacterUiModel
import com.goalpanzi.mission_mate.feature.board.util.ImageCompressor
import kotlinx.coroutines.flow.collectLatest
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun VerificationPreviewRoute(
    onClickClose: () -> Unit,
    onUploadSuccess: () -> Unit,
    viewModel: VerificationPreviewViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var showProgress by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest {
            when (it) {
                UploadEvent.Loading -> {
                    showProgress = true
                }
                UploadEvent.Success -> {
                    onUploadSuccess()
                }
                UploadEvent.Error -> {
                    showProgress = false
                }
            }
        }
    }

    VerificationPreviewScreen(
        isUploading = showProgress,
        onClickClose = onClickClose,
        uiState = uiState,
        onClickUpload = viewModel::uploadImage
    )
}

@Composable
fun VerificationPreviewScreen(
    isUploading: Boolean,
    onClickClose: () -> Unit,
    uiState: VerificationPreviewUiState,
    onClickUpload: (File) -> Unit
) {
    val context = LocalContext.current
    val dateTime = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val statusBarPaddingValue = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    var isVisibleSpacer by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        when (uiState) {
            VerificationPreviewUiState.Loading -> VerificationPreviewLoading()
            is VerificationPreviewUiState.Success -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ColorBlack_FF000000)
                        .statusBarsPadding()
                ) {

                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(uiState.imageUrl)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().clickableWithoutRipple {
                            isVisibleSpacer = !isVisibleSpacer
                        },
                        contentScale = ContentScale.Fit,
                        filterQuality = FilterQuality.None
                    )
                }
                if (isVisibleSpacer) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(statusBarPaddingValue + 80.dp)
                            .background(ColorBlack_FF000000.copy(alpha = 0.7f))
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
                            .statusBarsPadding()
                            .height(93.dp)
                            .padding(horizontal = 24.dp, vertical = 14.dp)
                    ) {
                        StableImage(
                            modifier = Modifier
                                .padding(top = 6.dp)
                                .size(28.dp)
                                .border(1.dp, ColorWhite_FFFFFFFF, CircleShape)
                                .paint(
                                    painter = painterResource(uiState.characterUiModel.backgroundId),
                                    contentScale = ContentScale.FillWidth
                                )
                                .padding(5.dp),
                            drawableResId = uiState.characterUiModel.imageId,
                            description = ""
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
                            onClick = {
                                onClickClose()
                                //setStatusBar(context, true)
                            },
                            modifier = Modifier.wrapContentSize()
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = com.goalpanzi.mission_mate.core.designsystem.R.drawable.ic_close),
                                contentDescription = "",
                                tint = ColorWhite_FFFFFFFF
                            )
                        }
                    }

                    UploadButton(
                        context = context,
                        filePath = uiState.imageUrl.toUri(),
                        isUploading = isUploading,
                        onClickUpload = onClickUpload
                    )
                }
            }
        }
    }
}

@Composable
fun BoxScope.UploadButton(
    context: Context,
    filePath: Uri,
    isUploading: Boolean,
    onClickUpload: (File) -> Unit
) {
    val multipleEventsCutter = remember { MultipleEventsCutter.get() }
    MissionMateButton(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(horizontal = 24.dp, vertical = 36.dp)
            .fillMaxWidth()
            .navigationBarsPadding(),
        buttonType = if(isUploading) MissionMateButtonType.DISABLED else MissionMateButtonType.ACTIVE,
        onClick = {
            multipleEventsCutter.processEvent {
                val file = ImageCompressor.getCompressedImage(context, filePath)
                onClickUpload(file)
            }
        }
    ) {
        if(isUploading){
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = ColorWhite_FFFFFFFF,
                strokeWidth = 3.dp
            )
        }else {
            Text(
                text = stringResource(id = R.string.upload),
                style = MissionMateTypography.body_lg_bold,
                color = ColorWhite_FFFFFFFF
            )
        }
    }
}

@Composable
fun VerificationPreviewLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .statusBarsPadding()
            .navigationBarsPadding()
            .focusable()
            .clickable {}
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ProgressBar() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun VerificationPreviewScreenPreview() {
    VerificationPreviewScreen(
        isUploading = false,
        onClickClose = {},
        uiState = VerificationPreviewUiState.Success(
            characterUiModel = CharacterUiModel.RABBIT,
            nickname = "닉네임",
            imageUrl = ""
        ),
        onClickUpload = {}
    )
}

@Preview
@Composable
fun VerificationPreviewScreenLoadingPreview() {
    VerificationPreviewScreen(
        isUploading = false,
        onClickClose = {},
        uiState = VerificationPreviewUiState.Loading,
        onClickUpload = {}
    )
}
