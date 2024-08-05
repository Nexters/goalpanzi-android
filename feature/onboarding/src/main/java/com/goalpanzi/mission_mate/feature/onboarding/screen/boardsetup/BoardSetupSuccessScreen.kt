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
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray2_FF4F505C
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray3_FF727484
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.feature.onboarding.R
import com.goalpanzi.mission_mate.feature.onboarding.component.StableImage

@Composable
fun BoardSetupSuccessScreen(
    onClickStart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(ColorWhite_FFFFFFFF)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            painter = painterResource(id = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_jeju),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = 48.dp),
                text = stringResource(id = R.string.onboarding_board_setup_success_title),
                style = MissionMateTypography.heading_sm_bold,
                color = ColorGray1_FF404249
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.onboarding_board_setup_success_description_top),
                    style = MissionMateTypography.title_xl_bold,
                    color = ColorGray3_FF727484,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(id = R.string.onboarding_board_setup_success_description_bottom),
                    style = MissionMateTypography.title_xl_regular,
                    color = ColorGray3_FF727484,
                    textAlign = TextAlign.Center
                )
            }
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