package com.goalpanzi.mission_mate.feature.onboarding.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorOrange_FFFF5732
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography

@Composable
fun OutlinedTextBox(
    text : String,
    modifier: Modifier = Modifier,
    borderStroke: BorderStroke = BorderStroke(1.dp, ColorOrange_FFFF5732),
    shape: Shape = RoundedCornerShape(50),
    contentPadding : PaddingValues = PaddingValues(vertical = 1.dp, horizontal = 14.dp),
    textStyle: TextStyle = MissionMateTypography.body_xl_regular,
    textColor : Color = ColorOrange_FFFF5732
){
    Box(
        modifier = modifier
            .border(borderStroke, shape)
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = text,
            style = textStyle,
            color = textColor
        )
    }
}