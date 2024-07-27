package com.goalpanzi.mission_mate.core.designsystem.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography

@Composable
fun MissionMateTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = Color(0xFFFFFFFF),
    textStyle: TextStyle = MissionMateTypography.body_lg_bold,
    enabled: Boolean = true
) {
    MissionMateButton(
        modifier = modifier,
        contentColor = textColor,
        enabled = enabled,
        onClick = onClick
    ) {
        Text(text = text, style = textStyle)
    }
}

@Composable
fun MissionMateButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Color(0xFFFF5F3C),
    disabledContainerColor: Color = Color(0xFFB3B3B3),
    contentColor: Color = Color(0xFFFFFFFF),
    disabledContentColor: Color = Color(0xFFFFFFFF),
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
            containerColor = containerColor,
            disabledContainerColor = disabledContainerColor,
            contentColor = contentColor,
            disabledContentColor = disabledContentColor
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
        text = "btn",
        onClick = {}
    )
}

@Preview
@Composable
fun PreviewTextButtonDisabled(){
    MissionMateTextButton(
        modifier = Modifier.fillMaxWidth(),
        text = "btn",
        enabled = false,
        onClick = {}
    )
}