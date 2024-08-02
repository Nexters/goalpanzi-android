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
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray5_80F5F6F9
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorRed_FFFF5858
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography

@Composable
fun MissionMateTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    @StringRes hintId : Int? = null,
    isError : Boolean = false,
    textStyle: TextStyle = MissionMateTypography.body_lg_regular,
    hintStyle: TextStyle = MissionMateTypography.body_lg_regular,
    textColor: Color = ColorGray1_FF404249,
    hintColor: Color = ColorGray3_FF727484,
    containerColor: Color = ColorWhite_FFFFFFFF,
    unfocusedHintColor: Color = ColorGray5_80F5F6F9,
    borderStroke: BorderStroke = BorderStroke(1.dp, ColorGray4_FFE5E5E5),
    focusedBorderStroke: BorderStroke = BorderStroke(1.dp, ColorGray4_FFE5E5E5),
    errorBorderStroke: BorderStroke = BorderStroke(2.dp, ColorRed_FFFF5858),
    shape: Shape = RoundedCornerShape(12.dp),
    isSingleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textAlign : Alignment = Alignment.CenterStart,
    contentPadding : PaddingValues = PaddingValues(horizontal = 16.dp)
) {
    var isFocused by remember { mutableStateOf(false) }
    BasicTextField(
        modifier = modifier
            .heightIn(min = 60.dp)
            .onFocusChanged {
                isFocused = it.isFocused
            },
        value = text,
        singleLine = isSingleLine,
        textStyle = textStyle.copy(
            color = textColor
        ),
        visualTransformation = visualTransformation,
        onValueChange = onValueChange,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .clip(shape)
                    .border(
                        border = if (isError) errorBorderStroke
                        else if (isFocused) focusedBorderStroke
                        else borderStroke,
                        shape = shape
                    )
                    .background(
                        if (!isFocused && text.isEmpty()) unfocusedHintColor
                        else containerColor
                    )
                    .padding(contentPadding),
                contentAlignment = textAlign
            ) {
                if(text.isBlank()){
                    Text(
                        text = hintId?.let { stringResource(id = it) } ?: "",
                        style = hintStyle,
                        color = hintColor
                    )
                }
                innerTextField()
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
    errorColor : Color = Color(0xFFFF6464)
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if(titleId != null){
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
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
            isError = isError
        )
        if(guidanceId != null){
            Text(
                text = stringResource(id = guidanceId) + if(useMaxLength) "(${text.length}/$maxLength)" else "",
                style = MissionMateTypography.body_md_regular,
                color = if(isError) errorColor else guidanceColor
            )
        }
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

