package com.goalpanzi.mission_mate.feature.onboarding.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.ext.dropShadow
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray3_FF727484
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography

@Composable
fun RowScope.OnboardingNavigationButton(
    @StringRes titleId : Int,
    @StringRes descriptionId : Int,
    @DrawableRes imageId: Int,
    onClick : () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20.dp)
){
    ElevatedButton(
        modifier = modifier.dropShadow(shape),
        onClick = onClick,
        shape = shape,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = ColorWhite_FFFFFFFF
        ),
        contentPadding = PaddingValues()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp,top = 20.dp),
                text = stringResource(id = titleId),
                style = MissionMateTypography.title_xl_bold,
                color = ColorGray1_FF404249
            )
            Text(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 12.dp),
                text = stringResource(id = descriptionId),
                style = MissionMateTypography.body_lg_regular,
                color = ColorGray3_FF727484
            )
            StableImage(
                modifier = Modifier
                    .padding(bottom = 12.dp, end = 8.dp)
                    .align(Alignment.End),
                drawableResId = imageId
            )
        }
    }
}