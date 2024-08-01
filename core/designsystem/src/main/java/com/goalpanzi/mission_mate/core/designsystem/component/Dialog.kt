package com.goalpanzi.mission_mate.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.goalpanzi.mission_mate.core.designsystem.R
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorBlack_FF000000
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray2_FF4F505C
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray3_FF727484
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography

@Composable
fun MissionMateDialog(
    @StringRes titleId: Int,
    onDismissRequest: () -> Unit,
    onClickOk : () -> Unit,
    modifier: Modifier = Modifier,
    @StringRes descriptionId: Int? = null,
    @StringRes okTextId: Int? = null,
    @StringRes cancelTextId: Int? = null,
    titleStyle: TextStyle = MissionMateTypography.title_xl_bold,
    descriptionStyle: TextStyle = MissionMateTypography.body_lg_regular,
    okTextStyle: TextStyle = MissionMateTypography.body_lg_bold,
    cancelTextStyle: TextStyle = MissionMateTypography.body_lg_bold,
    shape: Shape = RoundedCornerShape(20.dp),
    dialogInnerPadding: PaddingValues = PaddingValues(
        top = 40.dp,
        bottom = 34.dp,
        start = 24.dp,
        end = 24.dp
    )
) {
    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 20.dp)
                .clip(shape)
                .background(ColorWhite_FFFFFFFF)
                .padding(dialogInnerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = titleId),
                style = titleStyle,
                textAlign = TextAlign.Center,
                color = ColorGray1_FF404249
            )
            if (descriptionId != null) {
                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = stringResource(id = descriptionId),
                    style = descriptionStyle,
                    textAlign = TextAlign.Center,
                    color = ColorGray2_FF4F505C
                )
            }
            if (okTextId != null) {
                MissionMateTextButton(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .fillMaxWidth(),
                    textId = okTextId,
                    textStyle = okTextStyle,
                    onClick = onClickOk
                )
            }

            if (cancelTextId != null) {
                Text(
                    modifier = Modifier.padding(top = 20.dp)
                        .clickable(
                            interactionSource = null,
                            indication = null,
                            onClick = onDismissRequest
                        ),
                    text = stringResource(id = cancelTextId),
                    style = cancelTextStyle,
                    textAlign = TextAlign.Center,
                    color = ColorGray3_FF727484
                )
            }
        }
    }
}


@Composable
fun MissionMateDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    @StringRes okTextId: Int? = null,
    @StringRes cancelTextId: Int? = null,
    okTextStyle: TextStyle = MissionMateTypography.body_lg_bold,
    cancelTextStyle: TextStyle = MissionMateTypography.body_lg_bold,
    shape: Shape = RoundedCornerShape(20.dp),
    dialogInnerPadding: PaddingValues = PaddingValues(
        top = 40.dp,
        bottom = 34.dp,
        start = 24.dp,
        end = 24.dp
    ),
    content: @Composable () -> Unit
) {
    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 20.dp)
                .clip(shape)
                .background(ColorWhite_FFFFFFFF)
                .padding(dialogInnerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
            if (okTextId != null) {
                MissionMateTextButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textId = okTextId,
                    textStyle = okTextStyle,
                    onClick = {}
                )
            }

            if (cancelTextId != null) {
                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = stringResource(id = cancelTextId),
                    style = cancelTextStyle,
                    textAlign = TextAlign.Center,
                    color = ColorGray3_FF727484
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewMissionMateDialog() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        MissionMateDialog(
            modifier = Modifier.fillMaxWidth(),
            titleId = R.string.app_name,
            descriptionId = R.string.app_name,
            okTextId = R.string.app_name,
            cancelTextId = R.string.app_name,
            onClickOk = {},
            onDismissRequest = {}
        )
    }

}

@Preview
@Composable
fun PreviewMissionMateContentDialog() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        MissionMateDialog(
            modifier = Modifier.fillMaxWidth(),
            okTextId = R.string.app_name,
            cancelTextId = R.string.app_name,
            onDismissRequest = {}
        ) {
            Spacer(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .border(
                        1.dp,
                        ColorBlack_FF000000
                    )
            )
        }
    }

}