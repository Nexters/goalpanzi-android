package com.goalpanzi.mission_mate.feature.onboarding.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateDialog
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray2_FF4F505C
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.core.designsystem.theme.component.MissionMateTopAppBar
import com.goalpanzi.mission_mate.core.designsystem.theme.component.NavigationType
import com.goalpanzi.mission_mate.core.domain.common.model.user.CharacterType
import com.goalpanzi.mission_mate.core.domain.common.model.user.UserProfile
import com.goalpanzi.mission_mate.feature.onboarding.R
import com.goalpanzi.mission_mate.feature.onboarding.component.OnboardingNavigationButton
import com.goalpanzi.mission_mate.core.designsystem.component.OutlinedTextChip
import com.goalpanzi.mission_mate.core.designsystem.component.StableImage
import com.goalpanzi.mission_mate.feature.onboarding.model.OnboardingResultEvent
import com.goalpanzi.mission_mate.feature.onboarding.model.OnboardingUiModel
import kotlinx.coroutines.flow.collectLatest
import com.goalpanzi.mission_mate.core.designsystem.R as designSystemResource

@Composable
fun OnboardingRoute(
    modifier: Modifier = Modifier,
    onClickBoardSetup: () -> Unit,
    onClickInvitationCode: () -> Unit,
    onClickSetting: () -> Unit,
    onNavigateMissionBoard: (Long) -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val onboardingUiModel by viewModel.onboardingUiModel.collectAsStateWithLifecycle()
    var profileCreateSuccessData by remember { mutableStateOf<UserProfile?>(null) }

    LaunchedEffect(key1 = Unit) {
        viewModel.getJoinedMissions()

        viewModel.onboardingResultEvent.collect { result ->
            when (result) {
                is OnboardingResultEvent.SuccessWithJoinedMissions -> {
                    onNavigateMissionBoard(result.mission.missionId)
                }

                is OnboardingResultEvent.Error -> {
                    // 에러
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.profileCreateSuccessEvent.collectLatest {
            it?.let { profileCreateSuccessData = it }
        }
    }

    OnboardingScreen(
        onboardingUiModel = onboardingUiModel,
        modifier = modifier.fillMaxSize(),
        onClickBoardSetup = onClickBoardSetup,
        onClickInvitationCode = onClickInvitationCode,
        onClickSetting = onClickSetting
    )

    profileCreateSuccessData?.let {
        ProfileCreateSuccessDialog(
            nickname = it.nickname,
            character = it.characterType,
            onClickOk = {
                profileCreateSuccessData = null
            }
        )
    }
}

@Composable
fun OnboardingScreen(
    onboardingUiModel: OnboardingUiModel,
    onClickBoardSetup: () -> Unit,
    onClickInvitationCode: () -> Unit,
    onClickSetting: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.background(ColorWhite_FFFFFFFF)
    ) {
        StableImage(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            drawableResId = designSystemResource.drawable.background_jeju,
            description = null,
            contentScale = ContentScale.FillWidth
        )
        when (onboardingUiModel) {
            is OnboardingUiModel.Success -> {
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
                        style = MissionMateTypography.heading_sm_bold,
                        color = ColorGray1_FF404249
                    )
                    OutlinedTextChip(
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
                            drawableResId = when (onboardingUiModel.profileResponse.characterType) {
                                CharacterType.CAT -> designSystemResource.drawable.img_cat_selected
                                CharacterType.DOG -> designSystemResource.drawable.img_dog_selected
                                CharacterType.RABBIT -> designSystemResource.drawable.img_rabbit_selected
                                CharacterType.BEAR -> designSystemResource.drawable.img_bear_selected
                                CharacterType.PANDA -> designSystemResource.drawable.img_panda_selected
                                CharacterType.BIRD -> designSystemResource.drawable.img_bird_selected
                                else -> designSystemResource.drawable.img_rabbit_selected
                            },
                            contentScale = ContentScale.FillWidth
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxSize(348f / 390f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OnboardingNavigationButton(
                            modifier = Modifier.weight(162f / 324f),
                            titleId = R.string.onboarding_crating_board_title,
                            descriptionId = R.string.onboarding_crating_board_desription,
                            imageId = designSystemResource.drawable.ic_creating_board,
                            onClick = onClickBoardSetup
                        )
                        Spacer(
                            modifier = Modifier
                                .height(1.dp)
                                .weight(24f / 324f)
                        )
                        OnboardingNavigationButton(
                            modifier = Modifier.weight(162f / 324f),
                            titleId = R.string.onboarding_code_title,
                            descriptionId = R.string.onboarding_code_desription,
                            imageId = designSystemResource.drawable.ic_invitation_friend,
                            onClick = onClickInvitationCode
                        )
                    }
                }
            }

            is OnboardingUiModel.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
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
            imageVector = ImageVector.vectorResource(id = designSystemResource.drawable.ic_setting),
            contentDescription = "",
            tint = ColorGray1_FF404249
        )
    }
}

@Composable
fun ProfileCreateSuccessDialog(
    nickname: String,
    character: CharacterType,
    onClickOk: () -> Unit
) {
    MissionMateDialog(
        onDismissRequest = {},
        onClickOk = onClickOk,
        okTextId = R.string.confirm
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(
                    id = R.string.onboarding_profile_create_dialog_title,
                    nickname
                ),
                style = MissionMateTypography.title_xl_bold,
                textAlign = TextAlign.Center,
                color = ColorGray1_FF404249
            )
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = stringResource(id = R.string.onboarding_profile_create_dialog_description),
                style = MissionMateTypography.body_lg_regular,
                textAlign = TextAlign.Center,
                color = ColorGray2_FF4F505C
            )
            Box(
                modifier = Modifier
                    .padding(vertical = 32.dp)
                    .size(180.dp)
                    .paint(
                        painter = painterResource(
                            id = when (character) {
                                CharacterType.RABBIT -> designSystemResource.drawable.background_rabbit
                                CharacterType.CAT -> designSystemResource.drawable.background_cat
                                CharacterType.DOG -> designSystemResource.drawable.background_dog
                                CharacterType.PANDA -> designSystemResource.drawable.background_panda
                                CharacterType.BEAR -> designSystemResource.drawable.background_bear
                                CharacterType.BIRD -> designSystemResource.drawable.background_bird
                            }
                        ),
                        contentScale = ContentScale.FillWidth,
                    )
            ) {
                StableImage(
                    drawableResId = when (character) {
                        CharacterType.RABBIT -> designSystemResource.drawable.img_rabbit_default
                        CharacterType.CAT -> designSystemResource.drawable.img_cat_default
                        CharacterType.DOG -> designSystemResource.drawable.img_dog_default
                        CharacterType.PANDA -> designSystemResource.drawable.img_panda_default
                        CharacterType.BEAR -> designSystemResource.drawable.img_bear_default
                        CharacterType.BIRD -> designSystemResource.drawable.img_bird_default
                    },
                    description = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(18.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Preview
@Composable
fun OnboardingScreenPreview() {
    OnboardingScreen(
        onboardingUiModel = OnboardingUiModel.Success(
            UserProfile(
                nickname = "Test",
                characterType = CharacterType.CAT
            )
        ),
        onClickBoardSetup = {},
        onClickInvitationCode = {},
        onClickSetting = {}
    )
}

@Preview
@Composable
fun ProfileCreateSuccessDialogPreview() {
    ProfileCreateSuccessDialog(
        nickname = "Test",
        character = CharacterType.RABBIT,
        onClickOk = {}
    )
}
