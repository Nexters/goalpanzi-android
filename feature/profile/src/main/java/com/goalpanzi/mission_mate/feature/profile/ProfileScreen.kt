package com.goalpanzi.mission_mate.feature.profile

import android.app.Activity
import android.content.res.Configuration
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateButtonType
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateTextButton
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateTextFieldGroup
import com.goalpanzi.mission_mate.core.designsystem.component.StableImage
import com.goalpanzi.mission_mate.core.designsystem.ext.clickableWithoutRipple
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray5_FFF5F6F9
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateTopAppBar
import com.goalpanzi.mission_mate.core.designsystem.component.NavigationType
import com.goalpanzi.mission_mate.core.domain.common.model.user.CharacterType
import com.goalpanzi.mission_mate.feature.profile.model.CharacterListItem
import com.goalpanzi.mission_mate.feature.profile.model.CharacterListItem.Companion.createDefaultList
import com.goalpanzi.mission_mate.feature.profile.model.ProfileUiState
import com.luckyoct.feature.profile.R
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.flow.collectLatest

@Composable
fun profileViewModel(profileSettingType: ProfileSettingType): ProfileViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ProfileViewModelFactoryProvider::class.java
    ).profileViewModelFactory()

    return viewModel(factory = ProfileViewModel.provideFactory(factory, profileSettingType))
}

@Composable
fun ProfileRoute(
    modifier: Modifier = Modifier,
    profileSettingType: ProfileSettingType,
    onSaveSuccess: () -> Unit,
    onBackClick: (() -> Unit)? = null
) {
    val viewModel = profileViewModel(profileSettingType = profileSettingType)
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isNicknameDuplicated by viewModel.isNicknameDuplicated.collectAsStateWithLifecycle()
    val isNotChangedProfileInput by viewModel.isNotChangedProfileInput.collectAsStateWithLifecycle()

    val keyboardController = LocalSoftwareKeyboardController.current
    val localFocusManager = LocalFocusManager.current

    LaunchedEffect(true) {
        viewModel.isSaveSuccess.collectLatest {
            if (it) {
                if (profileSettingType == ProfileSettingType.SETTING) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.profile_update_success),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                onSaveSuccess()
            }
        }
    }

    Box(
        modifier = Modifier
            .clickableWithoutRipple {
                keyboardController?.hide()
                localFocusManager.clearFocus()
            }
            .fillMaxSize()
            .background(color = ColorWhite_FFFFFFFF)
            .systemBarsPadding()
            .navigationBarsPadding()
            .imePadding()
    ) {
        ProfileContent(
            modifier = modifier,
            uiState = uiState,
            profileSettingType = profileSettingType,
            isNotChangedProfileInput = isNotChangedProfileInput,
            onClickCharacter = { viewModel.selectCharacter(it) },
            onClickSave = {
                keyboardController?.hide()
                viewModel.saveProfile(it)
            },
            onBackClick = onBackClick,
            isNicknameDuplicated = isNicknameDuplicated
        )
    }
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    uiState: ProfileUiState,
    profileSettingType: ProfileSettingType,
    isNotChangedProfileInput: Boolean,
    onClickCharacter: (CharacterListItem) -> Unit = {},
    onClickSave: (String) -> Unit = {},
    onBackClick: (() -> Unit)? = null,
    isNicknameDuplicated: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = ColorWhite_FFFFFFFF)
    ) {
        if (profileSettingType == ProfileSettingType.SETTING) {
            MissionMateTopAppBar(
                navigationType = NavigationType.BACK,
                containerColor = ColorWhite_FFFFFFFF,
                onNavigationClick = { onBackClick?.invoke() }
            )
        }
        when (uiState) {
            ProfileUiState.Loading -> {
                ProfileLoading()
            }

            is ProfileUiState.Success -> {
                ProfileScreen(
                    modifier = modifier,
                    profileSettingType = profileSettingType,
                    initialNickname = uiState.nickname,
                    characters = uiState.characterList,
                    isNotChangedProfileInput = isNotChangedProfileInput,
                    onClickCharacter = onClickCharacter,
                    onClickSave = onClickSave,
                    isNicknameDuplicated = isNicknameDuplicated
                )
            }
        }
    }
}

@Composable
fun ColumnScope.ProfileScreen(
    modifier: Modifier = Modifier,
    initialNickname: String,
    profileSettingType: ProfileSettingType,
    characters: List<CharacterListItem>,
    isNotChangedProfileInput: Boolean,
    onClickCharacter: (CharacterListItem) -> Unit,
    onClickSave: (String) -> Unit,
    isNicknameDuplicated: Boolean,
) {
    var nicknameInput by remember { mutableStateOf(initialNickname) }
    val scrollState = rememberScrollState()
    val regex = Regex("^[가-힣ㅏ-ㅣㄱ-ㅎa-zA-Z0-9]{1,6}$")
    var invalidNicknameError by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current

    LaunchedEffect(nicknameInput) {
        if (nicknameInput.isEmpty()) return@LaunchedEffect
        invalidNicknameError = (nicknameInput.length > 6 || regex.matches(nicknameInput).not())
    }

    Column(
        modifier = modifier
            .padding(bottom = 18.dp)
            .weight(1f)
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .imePadding()
    ) {
        Text(
            text = stringResource(
                id = when (profileSettingType) {
                    ProfileSettingType.CREATE -> R.string.profile_create
                    ProfileSettingType.SETTING -> R.string.profile_setting_title
                }
            ),
            modifier = modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = if (profileSettingType == ProfileSettingType.SETTING) 0.dp else 56.dp),
            style = MissionMateTypography.heading_sm_bold,
            color = ColorGray1_FF404249
        )

        characters.find { it.isSelected }?.let {
            Box(
                modifier = modifier
                    .padding(top = 32.dp)
                    .size(configuration.screenWidthDp.dp * 0.55f)
                    .align(Alignment.CenterHorizontally)
            ) {
                CharacterLargeImage(
                    imageResId = it.defaultImageResId,
                    backgroundResId = it.backgroundResId
                )
            }
        }
        CharacterRow(
            characters = characters,
            configuration = configuration,
            onClick = onClickCharacter
        )

        MissionMateTextFieldGroup(
            modifier = modifier
                .padding(top = 38.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            text = nicknameInput,
            onValueChange = { nicknameInput = it },
            hintId = R.string.nickname_hint,
            guidanceId = if (isNicknameDuplicated) R.string.err_duplicated_nickname else R.string.nickname_input_guide,
            isError = invalidNicknameError || isNicknameDuplicated
        )
    }

    MissionMateTextButton(
        modifier = modifier
            .padding(bottom = 36.dp, start = 24.dp, end = 24.dp)
            .fillMaxWidth(),
        textId = R.string.save,
        buttonType = when (profileSettingType) {
            ProfileSettingType.CREATE -> {
                if (nicknameInput.trim().isEmpty() || invalidNicknameError) {
                    MissionMateButtonType.DISABLED
                } else {
                    MissionMateButtonType.ACTIVE
                }
            }

            ProfileSettingType.SETTING -> {
                if ((initialNickname == nicknameInput && isNotChangedProfileInput) ||
                    nicknameInput.trim().isEmpty() || invalidNicknameError
                ) {
                    MissionMateButtonType.DISABLED
                } else {
                    MissionMateButtonType.ACTIVE
                }
            }
        },
        onClick = { onClickSave(nicknameInput) }
    )
}

@Composable
fun CharacterLargeImage(
    modifier: Modifier = Modifier,
    @DrawableRes imageResId: Int,
    @DrawableRes backgroundResId: Int,
) {
    StableImage(
        drawableResId = imageResId,
        description = null,
        modifier = modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(backgroundResId),
                contentScale = ContentScale.FillWidth,
            ).padding(20.dp)
    )
}

@Composable
fun CharacterRow(
    modifier: Modifier = Modifier,
    configuration: Configuration,
    characters: List<CharacterListItem>,
    onClick: (CharacterListItem) -> Unit
) {
    val scrollState = rememberLazyListState()

    LaunchedEffect(key1 = characters) {
        characters.indexOfFirst { it.isSelected }.takeIf { it > 0 }?.let {
            scrollState.animateScrollToItem(it - 1)
        }
    }

    LazyRow(
        modifier = modifier
            .padding(top = 18.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        contentPadding = PaddingValues(horizontal = 24.dp),
        state = scrollState
    ) {
        items(items = characters, key = { it.selectedImageResId }) {
            CharacterElement(
                character = it,
                configuration = configuration,
                onClick = onClick
            )
        }
    }
}

@Composable
fun CharacterElement(
    modifier: Modifier = Modifier,
    character: CharacterListItem,
    configuration: Configuration = LocalConfiguration.current,
    onClick: (CharacterListItem) -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(
                width = configuration.screenWidthDp.dp * 100f / 390f,
                height = configuration.screenWidthDp.dp * 100f / 390f * 1.24f
            )
            .alpha(if (character.isSelected) 1f else 0.3f)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onClick(character) }
            )
    ) {
        StableImage(
            drawableResId = character.selectedImageResId,
            description = null,
        )

        Text(
            text = stringResource(id = character.nameResId),
            style = MissionMateTypography.body_md_bold,
            modifier = modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(color = ColorGray5_FFF5F6F9, shape = RoundedCornerShape(10.dp)),
            color = ColorGray1_FF404249,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ProfileLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun ColumnScope.ProfileScreenPreview() {
    ProfileScreen(
        profileSettingType = ProfileSettingType.CREATE,
        initialNickname = "",
        characters = createDefaultList(),
        onClickCharacter = {},
        onClickSave = {},
        isNicknameDuplicated = false,
        isNotChangedProfileInput = false
    )
}

@Preview
@Composable
fun CharacterElementPreview() {
    CharacterElement(
        character = CharacterListItem(
            type = CharacterType.CAT,
            selectedImageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_cat_selected,
            defaultImageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_cat_default,
            nameResId = R.string.cat_name,
            isSelected = false,
            backgroundResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_cat
        )
    )
}
