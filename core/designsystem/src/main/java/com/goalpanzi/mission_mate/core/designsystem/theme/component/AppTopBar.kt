package com.goalpanzi.mission_mate.core.designsystem.theme.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.R
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray3_FF727484
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography

enum class NavigationType {
    BACK, NONE
}

@Composable
fun MissionMateTopAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    navigationType: NavigationType,
    onNavigationClick: () -> Unit = {},
    containerColor: Color = MaterialTheme.colorScheme.surfaceDim,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    leftActionButtons: @Composable (() -> Unit)? = null,
    rightActionButtons: @Composable () -> Unit = {},
) {
    CompositionLocalProvider(LocalContentColor provides contentColor) {
        val icon: @Composable (Modifier, imageVector: ImageVector) -> Unit =
            { modifier, imageVector ->
                IconButton(
                    onClick = onNavigationClick,
                    modifier = modifier.wrapContentSize()
                ) {
                    Icon(
                        imageVector = imageVector,
                        contentDescription = "",
                        tint = ColorGray3_FF727484
                    )
                }
            }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(containerColor)
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            when (navigationType) {
                NavigationType.BACK -> {
                    icon(
                        Modifier.align(Alignment.CenterStart),
                        ImageVector.vectorResource(id = R.drawable.ic_arrow_left)
                    )
                }

                NavigationType.NONE -> {
                    leftActionButtons?.invoke()
                    Box(
                        modifier = modifier.height(48.dp)
                    )
                }
            }
            title?.let {
                Text(
                    text = it,
                    modifier = Modifier.align(Alignment.Center),
                    style = MissionMateTypography.title_lg_bold,
                    color = ColorGray1_FF404249
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
            ) {
                rightActionButtons()
            }
        }
    }
}

@Preview
@Composable
fun AppTopBarBackPreview() {
    MissionMateTopAppBar(
        navigationType = NavigationType.BACK,
        onNavigationClick = {},
        containerColor = Color.White,
        title = stringResource(id = R.string.app_name)
    )
}

@Preview
@Composable
fun AppTopBarNonePreview() {
    MissionMateTopAppBar(
        navigationType = NavigationType.NONE,
        onNavigationClick = {},
        containerColor = Color.White,
        rightActionButtons = {
            IconButton(
                onClick = {},
                modifier = Modifier.wrapContentSize()
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_setting),
                    contentDescription = "",
                )
            }
        }
    )
}