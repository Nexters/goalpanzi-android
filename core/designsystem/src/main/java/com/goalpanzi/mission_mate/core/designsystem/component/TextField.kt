package com.goalpanzi.mission_mate.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.R
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray3_FF727484
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray4_FFE5E5E5
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray5_FFF5F6F9
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorRed_FFFF5858
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography

@Composable
fun MissionMateTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    @StringRes hintId : Int? = null,
    @StringRes guidanceId : Int? = null,
    isError : Boolean = false,
    useMaxLength : Boolean = false,
    textStyle: TextStyle = MissionMateTypography.body_lg_regular,
    hintStyle: TextStyle = MissionMateTypography.body_lg_regular,
    textColor: Color = ColorGray1_FF404249,
    hintColor: Color = ColorGray3_FF727484,
    guidanceColor : Color = Color(0xFF4F505C),
    errorColor : Color = Color(0xFFFF6464),
    containerColor: Color = ColorWhite_FFFFFFFF,
    unfocusedContainerColor: Color = ColorGray5_FFF5F6F9,
    unfocusedHintColor: Color = ColorGray3_FF727484,
    borderStroke: BorderStroke = BorderStroke(1.dp, ColorGray5_FFF5F6F9),
    focusedBorderStroke: BorderStroke = BorderStroke(1.dp, ColorGray4_FFE5E5E5),
    errorBorderStroke: BorderStroke = BorderStroke(2.dp, ColorRed_FFFF5858),
    shape: Shape = RoundedCornerShape(12.dp),
    maxLength : Int = Int.MAX_VALUE,
    isSingleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textAlign : Alignment = Alignment.CenterStart,
    contentPadding : PaddingValues = PaddingValues(horizontal = 16.dp)
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
            color = textColor
        ),
        visualTransformation = visualTransformation,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        onValueChange = onValueChange,
        decorationBox = { innerTextField ->
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 60.dp)
                        .clip(shape)
                        .border(
                            border = if (isError) errorBorderStroke
                            else if (isFocused) focusedBorderStroke
                            else borderStroke,
                            shape = shape
                        )
                        .background(
                            if(text.isNotEmpty()) containerColor
                            else if (!isFocused) unfocusedContainerColor
                            else containerColor
                        )
                        .padding(contentPadding),
                    contentAlignment = textAlign
                ) {
                    if(text.isBlank()){
                        Text(
                            text = hintId?.let { stringResource(id = it) } ?: "",
                            style = hintStyle,
                            color = if(isFocused) hintColor else unfocusedHintColor
                        )
                    }
                    innerTextField()
                }
                if(guidanceId != null){
                    Text(
                        text = stringResource(id = guidanceId) + if(useMaxLength) "(${text.length}/$maxLength)" else "",
                        style = MissionMateTypography.body_md_regular,
                        color = if(isError) errorColor else guidanceColor
                    )
                }
            }
        }
    )
}

@Composable
fun MissionMateTextFieldGroup(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    useMaxLength : Boolean = false,
    @StringRes titleId : Int? = null,
    @StringRes hintId : Int? = null,
    @StringRes guidanceId : Int? = null,
    maxLength : Int = Int.MAX_VALUE,
    isError : Boolean = false,
    titleColor : Color = Color(0xFF4F505C),
    guidanceColor : Color = Color(0xFF4F505C),
    errorColor : Color = Color(0xFFFF6464),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if(titleId != null){
            Text(
                text = stringResource(id = titleId),
                style = MissionMateTypography.body_md_bold,
                color = titleColor
            )
        }
        MissionMateTextField(
            text = text,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            hintId = hintId,
            isError = isError,
            useMaxLength = useMaxLength,
            guidanceId = guidanceId,
            maxLength = maxLength,
            guidanceColor = guidanceColor,
            errorColor = errorColor,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )
    }
}

@Composable
@Preview
fun PreviewMissionMateTextField(){
    MissionMateTextField(
        modifier = Modifier.fillMaxWidth(),
        text = "",
        hintId = R.string.app_name,
        onValueChange = {}
    )
}

@Composable
@Preview
fun PreviewMissionMateTextFieldGroup(){
    MissionMateTextFieldGroup(
        text = "Goalpanzi",
        onValueChange = {},
        titleId = R.string.app_name,
        guidanceId = R.string.app_name,
    )
}

@Composable
@Preview
fun PreviewMissionMateTextFieldGroupWithMaxLength(){
    MissionMateTextFieldGroup(
        text = "Goalpanzi",
        onValueChange = {},
        titleId = R.string.app_name,
        guidanceId = R.string.app_name,
        useMaxLength = true,
        maxLength = 12
    )
}

@Composable
@Preview
fun PreviewMissionMateTextFieldGroupError(){
    MissionMateTextFieldGroup(
        text = "Goalpanzi",
        onValueChange = {},
        titleId = R.string.app_name,
        guidanceId = R.string.app_name,
        isError = true
    )
}

