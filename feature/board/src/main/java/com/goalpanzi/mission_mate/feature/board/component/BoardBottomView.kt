package com.goalpanzi.mission_mate.feature.board.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateTextButton
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray2_FF4F505C
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.feature.board.R
import com.goalpanzi.mission_mate.feature.onboarding.component.StableImage

@Composable
fun BoardBottomView(
    onClickButton : () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .navigationBarsPadding()
            .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
            .background(ColorWhite_FFFFFFFF.copy(alpha = 0.7f))
            .padding(top = 16.dp, bottom = 36.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            StableImage(drawableResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.ic_time)
            Text(
                text = stringResource(id = R.string.board_verification_am_time_limit),
                style = MissionMateTypography.body_lg_bold,
                color = ColorGray2_FF4F505C
            )
        }
        MissionMateTextButton(
            modifier = Modifier.fillMaxWidth(),
            textId = R.string.board_verification,
            onClick = onClickButton
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewBoardBottomView(){
    BoardBottomView(
        onClickButton = {}
    )
}