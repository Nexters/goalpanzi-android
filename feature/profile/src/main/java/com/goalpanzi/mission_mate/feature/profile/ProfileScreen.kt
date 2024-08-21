package com.goalpanzi.mission_mate.feature.profile

import android.app.Activity
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import com.goalpanzi.mission_mate.core.designsystem.ext.clickableWithoutRipple
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray5_FFF5F6F9
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.core.designsystem.theme.component.MissionMateTopAppBar
import com.goalpanzi.mission_mate.core.designsystem.theme.component.NavigationType
import com.goalpanzi.core.model.CharacterType
import com.goalpanzi.mission_mate.feature.profile.model.CharacterListItem
import com.goalpanzi.mission_mate.feature.profile.model.ProfileUiState
import com.luckyoct.feature.profile.R
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.delay
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
    val isInvalidNickname by viewModel.isInvalidNickname.collectAsStateWithLifecycle()
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
            isInvalidNickname = isInvalidNickname,
            resetNicknameErrorState = { viewModel.resetNicknameErrorState() }
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
    isInvalidNickname: Boolean,
    resetNicknameErrorState: () -> Unit
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
                    isInvalidNickname = isInvalidNickname,
                    resetNicknameErrorState = resetNicknameErrorState
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
    isInvalidNickname: Boolean,
    resetNicknameErrorState: () -> Unit = {}
) {
    var nicknameInput by remember { mutableStateOf(initialNickname) }
    val scrollState = rememberScrollState()
    val regex = Regex("^[가-힣ㅏ-ㅣㄱ-ㅎa-zA-Z0-9]{1,6}$")

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
                .padding(top = 48.dp),
            style = MissionMateTypography.heading_sm_bold,
            color = ColorGray1_FF404249
        )

        characters.find { it.isSelected }?.let {
            Box(
                modifier = modifier
                    .padding(top = 32.dp)
                    .size(220.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                CharacterLargeImage(
                    imageResId = it.imageResId,
                    backgroundResId = it.backgroundResId
                )
            }
        }
        CharacterRow(
            characters = characters,
            onClick = onClickCharacter
        )

        MissionMateTextFieldGroup(
            modifier = modifier
                .padding(top = 38.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            text = nicknameInput,
            onValueChange = {
                if (regex.matches(it) || it.isEmpty()) {
                    nicknameInput = it
                }
                resetNicknameErrorState()
            },
            hintId = R.string.nickname_hint,
            guidanceId = if (isInvalidNickname) R.string.err_duplicated_nickname else R.string.nickname_input_guide,
            isError = isInvalidNickname
        )
    }

    MissionMateTextButton(
        modifier = modifier
            .padding(bottom = 36.dp, start = 24.dp, end = 24.dp)
            .fillMaxWidth(),
        textId = R.string.save,
        buttonType =
        if (profileSettingType == ProfileSettingType.CREATE) {
            if (nicknameInput.trim().isEmpty()) {
                MissionMateButtonType.DISABLED
            } else {
                MissionMateButtonType.ACTIVE
            }
        } else {
            if ((initialNickname == nicknameInput && isNotChangedProfileInput) ||
                nicknameInput.trim().isEmpty()
            ) {
                MissionMateButtonType.DISABLED
            } else {
                MissionMateButtonType.ACTIVE
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
    Image(
        painter = painterResource(id = imageResId),
        contentDescription = null,
        modifier = modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(backgroundResId),
                contentScale = ContentScale.FillWidth,
            )
    )
}

@Composable
fun CharacterRow(
    modifier: Modifier = Modifier,
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
        items(items = characters, key = { it.imageResId }) {
            CharacterElement(
                character = it,
                onClick = onClick
            )
        }
    }
}

@Composable
fun CharacterElement(
    modifier: Modifier = Modifier,
    character: CharacterListItem,
    onClick: (CharacterListItem) -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(width = 100.dp, height = 124.dp)
            .alpha(if (character.isSelected) 1f else 0.3f)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onClick(character) }
            )
    ) {
        Image(
            painter = painterResource(id = character.imageResId),
            contentDescription = null,
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
        characters = listOf(
            CharacterListItem(
                type = CharacterType.RABBIT,
                imageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_rabbit_selected,
                nameResId = R.string.rabbit_name,
                isSelected = true,
                backgroundResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_rabbit
            ),
            CharacterListItem(
                type = CharacterType.CAT,
                imageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_cat_selected,
                nameResId = R.string.cat_name,
                isSelected = false,
                backgroundResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_cat
            ),
            CharacterListItem(
                type = CharacterType.DOG,
                imageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_dog_selected,
                nameResId = R.string.dog_name,
                isSelected = false,
                backgroundResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_dog
            ),
            CharacterListItem(
                type = CharacterType.PANDA,
                imageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_panda_selected,
                nameResId = R.string.panda_name,
                isSelected = false,
                backgroundResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_panda
            ),
        ),
        onClickCharacter = {},
        onClickSave = {},
        isInvalidNickname = false,
        isNotChangedProfileInput = false
    )
}

@Preview
@Composable
fun CharacterElementPreview() {
    CharacterElement(
        character = CharacterListItem(
            type = CharacterType.CAT,
            imageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_cat_selected,
            nameResId = R.string.cat_name,
            isSelected = false,
            backgroundResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_cat
        )
    )
}