package com.goalpanzi.mission_mate.core.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorDisabled_FFB3B3B3
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray4_FFE5E5E5
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray5_FFF5F6F9
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorRed_FFFF5858
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography

@Composable
fun InvitationCodeTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String? = null,
    isError: Boolean = false,
    textStyle: TextStyle = MissionMateTypography.heading_md_bold,
    hintStyle: TextStyle = MissionMateTypography.heading_md_bold,
    textColor: Color = ColorGray1_FF404249,
    hintColor: Color = ColorDisabled_FFB3B3B3,
    containerColor: Color = ColorWhite_FFFFFFFF,
    unfocusedHintColor: Color = ColorGray5_FFF5F6F9,
    borderStroke: BorderStroke = BorderStroke(1.dp, ColorGray5_FFF5F6F9),
    focusedBorderStroke: BorderStroke = BorderStroke(1.dp, ColorGray4_FFE5E5E5),
    errorBorderStroke: BorderStroke = BorderStroke(2.dp, ColorRed_FFFF5858),
    shape: Shape = RoundedCornerShape(12.dp),
    isSingleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textAlign: Alignment = Alignment.Center,
    contentPadding: PaddingValues = PaddingValues(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    readOnly : Boolean = false
) {
    var isFocused by remember { mutableStateOf(false) }
    BasicTextField(
        modifier = modifier
            .onFocusChanged {
                isFocused = it.isFocused
            },
        value = text,
        singleLine = isSingleLine,
        textStyle = textStyle.copy(
            color = textColor,
            textAlign = TextAlign.Center,
            lineHeight = 40.sp
        ),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        onValueChange = onValueChange,
        readOnly = readOnly,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .clip(shape)
                    .border(
                        border = if (isError) errorBorderStroke
                        else if (isFocused || text.isNotEmpty()) focusedBorderStroke
                        else borderStroke,
                        shape = shape
                    )
                    .background(
                        if(text.isNotEmpty()) containerColor
                        else if (!isFocused ) unfocusedHintColor
                        else containerColor
                    )
                    .padding(contentPadding),
                contentAlignment = textAlign
            ) {
                if (text.isBlank() && !isFocused) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = hint ?: "0",
                        style = hintStyle,
                        color = hintColor,
                        textAlign = TextAlign.Center
                    )
                }
                innerTextField()
            }

        }
    )
}

@Composable
fun InvitationCodeText(
    text: String,
    modifier: Modifier = Modifier,
    hint: String? = null,
    isError: Boolean = false,
    textStyle: TextStyle = MissionMateTypography.heading_md_bold,
    hintStyle: TextStyle = MissionMateTypography.heading_md_bold,
    textColor: Color = ColorGray1_FF404249,
    hintColor: Color = ColorDisabled_FFB3B3B3,
    containerColor: Color = ColorWhite_FFFFFFFF,
    unfocusedHintColor: Color = ColorGray5_FFF5F6F9,
    borderStroke: BorderStroke = BorderStroke(1.dp, ColorGray5_FFF5F6F9),
    focusedBorderStroke: BorderStroke = BorderStroke(1.dp, ColorGray4_FFE5E5E5),
    errorBorderStroke: BorderStroke = BorderStroke(2.dp, ColorRed_FFFF5858),
    shape: Shape = RoundedCornerShape(12.dp),
    textAlign: Alignment = Alignment.Center,
    contentPadding: PaddingValues = PaddingValues()
) {
    InvitationCodeBox(
        modifier = modifier,
        text = text,
        isFocused = false,
        hint = hint,
        isError = isError,
        hintStyle = hintStyle,
        hintColor = hintColor,
        containerColor = containerColor,
        unfocusedHintColor = unfocusedHintColor,
        borderStroke = borderStroke,
        focusedBorderStroke = focusedBorderStroke,
        errorBorderStroke = errorBorderStroke,
        shape = shape,
        textAlign = textAlign,
        contentPadding = contentPadding,
        innerTextField = {
            Text(
                text = text,
                style = textStyle,
                color = textColor
            )
        }
    )
}

@Composable
fun InvitationCodeBox(
    text: CharSequence,
    isFocused: Boolean,
    modifier: Modifier = Modifier,
    hint: String? = null,
    isError: Boolean = false,
    hintStyle: TextStyle = MissionMateTypography.heading_md_bold,
    hintColor: Color = ColorDisabled_FFB3B3B3,
    containerColor: Color = ColorWhite_FFFFFFFF,
    unfocusedHintColor: Color = ColorGray5_FFF5F6F9,
    borderStroke: BorderStroke = BorderStroke(1.dp, ColorGray5_FFF5F6F9),
    focusedBorderStroke: BorderStroke = BorderStroke(1.dp, ColorGray4_FFE5E5E5),
    errorBorderStroke: BorderStroke = BorderStroke(2.dp, ColorRed_FFFF5858),
    shape: Shape = RoundedCornerShape(12.dp),
    textAlign: Alignment = Alignment.Center,
    contentPadding: PaddingValues = PaddingValues(),
    innerTextField: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier
            .clip(shape)
            .border(
                border = if (isError) errorBorderStroke
                else if (isFocused || text.isNotEmpty()) focusedBorderStroke
                else borderStroke,
                shape = shape
            )
            .background(
                if (text.isNotEmpty()) containerColor
                else if (!isFocused) unfocusedHintColor
                else containerColor
            )
            .padding(contentPadding),
        contentAlignment = textAlign
    ) {
        if (text.isBlank() && !isFocused) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = hint ?: "0",
                style = hintStyle,
                color = hintColor,
                textAlign = TextAlign.Center
            )
        }
        innerTextField()
    }
}

