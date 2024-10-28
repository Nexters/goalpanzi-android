package com.goalpanzi.mission_mate.feature.onboarding.screen.boardsetup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateButtonType
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateTextButton
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.feature.onboarding.R
import com.goalpanzi.mission_mate.core.designsystem.component.OutlinedTextChip
import com.goalpanzi.mission_mate.core.designsystem.component.StableImage

@Composable
fun BoardSetupSuccessScreen(
    onClickStart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(ColorWhite_FFFFFFFF)
    ) {
        StableImage(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            drawableResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_jeju,
            description = null,
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = 56.dp, bottom = 52.dp),
                text = stringResource(id = R.string.onboarding_board_setup_success_title),
                style = MissionMateTypography.heading_sm_bold,
                color = ColorGray1_FF404249,
                textAlign = TextAlign.Center
            )
            OutlinedTextChip(
                text = stringResource(id = R.string.onboarding_level_1),
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 7.dp)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                StableImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    drawableResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.image_jeju_success,
                    contentScale = ContentScale.FillWidth
                )
            }

            Text(
                text = stringResource(id = R.string.onboarding_board_setup_success_description),
                style = MissionMateTypography.body_xl_regular,
                color = ColorGray1_FF404249,
                textAlign = TextAlign.Center
            )
            MissionMateTextButton(
                modifier = Modifier
                    .padding(bottom = 36.dp, start = 10.dp, end = 10.dp, top = 71.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                buttonType = MissionMateButtonType.ACTIVE,
                textId = R.string.start,
                onClick = onClickStart
            )
        }
    }

}

@Preview
@Composable
fun PreviewBoardSetupSuccessScreen(){
    BoardSetupSuccessScreen(
        onClickStart = {},
        modifier = Modifier.fillMaxSize()
    )
}
