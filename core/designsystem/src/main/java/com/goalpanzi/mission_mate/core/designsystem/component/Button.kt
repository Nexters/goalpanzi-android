package com.goalpanzi.mission_mate.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.R
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorDisabled_FFB3B3B3
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorOrange_FFFF5732
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography

enum class MissionMateButtonType(
    val containerColor : Color,
    val contentColor : Color = ColorWhite_FFFFFFFF,
) {
    ACTIVE(containerColor = ColorOrange_FFFF5732),
    SECONDARY(containerColor = ColorGray1_FF404249),
    DISABLED(containerColor = ColorDisabled_FFB3B3B3)
}

@Composable
fun MissionMateTextButton(
    @StringRes textId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonType: MissionMateButtonType = MissionMateButtonType.ACTIVE,
    textColor: Color = Color(0xFFFFFFFF),
    textStyle: TextStyle = MissionMateTypography.body_lg_bold,
    enabled: Boolean = true
) {
    MissionMateButton(
        modifier = modifier,
        buttonType = buttonType,
        enabled = enabled,
        onClick = onClick
    ) {
        Text(
            text = stringResource(id = textId),
            style = textStyle,
            color = textColor
        )
    }
}

@Composable
fun MissionMateButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonType: MissionMateButtonType = MissionMateButtonType.ACTIVE,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(30.dp),
    contentPadding: PaddingValues = PaddingValues(vertical = 18.dp, horizontal = 30.dp),
    content: @Composable () -> Unit
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonColors(
            containerColor = buttonType.containerColor,
            disabledContainerColor = MissionMateButtonType.DISABLED.containerColor,
            contentColor = buttonType.contentColor,
            disabledContentColor = MissionMateButtonType.DISABLED.contentColor
        ),
        contentPadding = contentPadding,
        onClick = onClick
    ) {
        content()
    }
}

@Preview
@Composable
fun PreviewTextButton(){
    MissionMateTextButton(
        modifier = Modifier.fillMaxWidth(),
        textId = R.string.app_name,
        onClick = {}
    )
}

@Preview
@Composable
fun PreviewSecondaryTextButton(){
    MissionMateTextButton(
        modifier = Modifier.fillMaxWidth(),
        textId = R.string.app_name,
        buttonType = MissionMateButtonType.SECONDARY,
        onClick = {}
    )
}


@Preview
@Composable
fun PreviewTextButtonDisabled(){
    MissionMateTextButton(
        modifier = Modifier.fillMaxWidth(),
        textId = R.string.app_name,
        enabled = false,
        onClick = {}
    )
}