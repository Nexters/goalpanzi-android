package com.goalpanzi.mission_mate.feature.onboarding.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.core.designsystem.theme.component.MissionMateTopAppBar
import com.goalpanzi.mission_mate.core.designsystem.theme.component.NavigationType
import com.goalpanzi.mission_mate.feature.onboarding.R
import com.goalpanzi.mission_mate.feature.onboarding.component.OnboardingNavigationButton
import com.goalpanzi.mission_mate.feature.onboarding.component.OutlinedTextBox
import com.goalpanzi.mission_mate.feature.onboarding.component.StableImage
import com.goalpanzi.mission_mate.core.designsystem.R as designSystemResource

@Composable
fun OnboardingRoute(
    modifier: Modifier = Modifier,
    onClickBoardSetup: () -> Unit,
    onClickInvitationCode: () -> Unit,
    onClickSetting: () -> Unit
) {
    OnboardingScreen(
        modifier = modifier.fillMaxSize(),
        onClickBoardSetup = onClickBoardSetup,
        onClickInvitationCode = onClickInvitationCode,
        onClickSetting = onClickSetting
    )
}

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onClickBoardSetup: () -> Unit,
    onClickInvitationCode: () -> Unit,
    onClickSetting: () -> Unit
) {
    Box(
        modifier = modifier.background(ColorWhite_FFFFFFFF)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            painter = painterResource(id = designSystemResource.drawable.background_jeju),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = modifier
                .statusBarsPadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MissionMateTopAppBar(
                modifier = modifier,
                navigationType = NavigationType.NONE,
                containerColor = Color.Transparent,
                rightActionButtons = {
                    TopBarSetting(
                        onClick = { onClickSetting() }
                    )
                }
            )
            Text(
                modifier = Modifier.padding(bottom = 52.dp),
                text = stringResource(id = R.string.onboarding_ready_title),
                textAlign = TextAlign.Center,
                style = MissionMateTypography.heading_sm_regular,
                color = ColorGray1_FF404249
            )
            OutlinedTextBox(
                text = stringResource(id = R.string.onboarding_level_1),
                modifier = Modifier.padding(bottom = 23.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 7.dp)
                    .wrapContentHeight(),
                contentAlignment = Alignment.BottomCenter
            ) {
                StableImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    drawableResId = designSystemResource.drawable.img_jeju_theme,
                    contentScale = ContentScale.FillWidth
                )
                StableImage(
                    modifier = Modifier
                        .fillMaxWidth(0.564f)
                        .wrapContentHeight(),
                    drawableResId = designSystemResource.drawable.img_rabbit_selected,
                    contentScale = ContentScale.FillWidth
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                OnboardingNavigationButton(
                    modifier = Modifier.weight(1f),
                    titleId = R.string.onboarding_crating_board_title,
                    descriptionId = R.string.onboarding_crating_board_desription,
                    imageId = designSystemResource.drawable.ic_creating_board,
                    onClick = onClickBoardSetup
                )
                OnboardingNavigationButton(
                    modifier = Modifier.weight(1f),
                    titleId = R.string.onboarding_code_title,
                    descriptionId = R.string.onboarding_code_desription,
                    imageId = designSystemResource.drawable.ic_invitation_friend,
                    onClick = onClickInvitationCode
                )
            }
        }
    }
}

@Composable
fun TopBarSetting(
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.wrapContentSize()
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = com.goalpanzi.mission_mate.core.designsystem.R.drawable.ic_setting),
            contentDescription = "",
            tint = ColorGray1_FF404249
        )
    }
}

@Preview
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(
        onClickBoardSetup = {},
        onClickInvitationCode = {},
        onClickSetting = {}
    )
}
