package com.goalpanzi.mission_mate.core.designsystem.component

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography

@Composable
fun MissionMateTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint : String = "",
    isError : Boolean = false,
    textStyle: TextStyle = MissionMateTypography.body_lg_bold,
    hintStyle: TextStyle = MissionMateTypography.body_lg_regular,
    textColor: Color = Color(0xFF313239),
    hintColor: Color = Color(0xFF727484),
    containerColor: Color = Color(0xFFF5F6F9),
    errorContainerColor: Color = Color(0x4DFF6464),
    borderStroke: BorderStroke = BorderStroke(1.dp,Color(0xFFF5F6F9)),
    focusedBorderStroke: BorderStroke = BorderStroke(1.dp, Color(0xFF000000)),
    errorBorderStroke: BorderStroke = BorderStroke(1.dp, Color(0xFFFF6464)),
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
                        if (isError) errorContainerColor
                        else containerColor
                    )
                    .padding(contentPadding),
                contentAlignment = textAlign
            ) {
                if(text.isBlank()){
                    Text(
                        text = hint,
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
    useTitle : Boolean = false,
    useGuidance : Boolean = false,
    useMaxLength : Boolean = false,
    title : String = "",
    hint : String = "",
    guidance : String = "",
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
        if(useTitle){
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = title,
                style = MissionMateTypography.body_md_bold,
                color = titleColor
            )
        }
        MissionMateTextField(
            text = text,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            hint = hint,
            isError = isError
        )
        if(useGuidance){
            Text(
                text = guidance + if(useMaxLength) "(${text.length}/$maxLength)" else "",
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
        text = "Goalpanzi",
        onValueChange = {}
    )
}

@Composable
@Preview
fun PreviewMissionMateTextFieldGroup(){
    MissionMateTextFieldGroup(
        text = "Goalpanzi",
        onValueChange = {},
        useTitle = true,
        useGuidance = true,
        title = "Mission-Mate",
        guidance = "4~10자, 한글, 영문 또는 숫자를 입력하세요."
    )
}

@Composable
@Preview
fun PreviewMissionMateTextFieldGroupWithMaxLength(){
    MissionMateTextFieldGroup(
        text = "Goalpanzi",
        onValueChange = {},
        useTitle = true,
        useGuidance = true,
        title = "Mission-Mate",
        guidance = "4~12자 이내로 입력하세요. ",
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
        useTitle = true,
        useGuidance = true,
        title = "Mission-Mate",
        guidance = "중복된 닉네임이에요.",
        isError = true
    )
}

