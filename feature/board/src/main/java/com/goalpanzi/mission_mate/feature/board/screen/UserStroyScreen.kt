package com.goalpanzi.mission_mate.feature.board.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.goalpanzi.mission_mate.core.designsystem.component.StableImage
import com.goalpanzi.mission_mate.core.designsystem.ext.clickableWithoutRipple
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorBlack_FF000000
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionmateTheme
import com.goalpanzi.mission_mate.core.navigation.model.image.MissionMateImages
import com.goalpanzi.mission_mate.feature.board.model.CharacterUiModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun UserStoryScreen(
    images: MissionMateImages,
    onClickClose: () -> Unit
) {
    if(images.isEmpty()) return

    val characterUiModel = images[0].userCharacter.let { CharacterUiModel.valueOf(it) }
    val verifiedAt = images[0].verifiedAt
    val imageUrl = images[0].imageUrl
    val nickname = images[0].nickname

    val dateTime = LocalDateTime.parse(verifiedAt)
    val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val statusBarPaddingValue = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    var isVisibleSpacer by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(ColorBlack_FF000000)
                .statusBarsPadding()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(URLDecoder.decode(imageUrl, StandardCharsets.UTF_8.toString()))
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clickableWithoutRipple {
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
        }
    }
}

@Preview
@Composable
fun UserStoryScreenPreview() {
    MissionmateTheme {
        UserStoryScreen(
            images = MissionMateImages(
                emptyList()
            ),
            onClickClose = {}
        )
    }
}
