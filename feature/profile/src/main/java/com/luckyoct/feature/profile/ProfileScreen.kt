package com.luckyoct.feature.profile

import android.app.Activity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateTextButton
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateTextFieldGroup
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray5_FFF5F6F9
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorPink_FFFFE4E4
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.core.designsystem.theme.component.MissionMateTopAppBar
import com.goalpanzi.mission_mate.core.designsystem.theme.component.NavigationType
import com.luckyoct.feature.profile.model.CharacterListItem
import com.luckyoct.feature.profile.model.ProfileEvent
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
    val characters = viewModel.characters.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.event.collectLatest {
            when (it) {
                ProfileEvent.Success -> onSaveSuccess()
                else -> return@collectLatest
            }
        }
    }

    ProfileScreen(
        profileSettingType = profileSettingType,
        characters = characters.value,
        onclickCharacter = { viewModel.selectCharacter(it) },
        onClickSave = { viewModel.saveProfile(it) },
        onBackClick = { onBackClick?.invoke() }
    )
}

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    profileSettingType: ProfileSettingType,
    characters: List<CharacterListItem>,
    onclickCharacter: (CharacterListItem) -> Unit,
    onClickSave: (String) -> Unit,
    onBackClick: (() -> Unit)? = null
) {

    var nicknameInput by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val regex = Regex("^[가-힣ㅏ-ㅣㄱ-ㅎa-zA-Z0-9]{1,6}$")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = ColorWhite_FFFFFFFF)
            .imePadding()
            .statusBarsPadding()
    ) {
        if (profileSettingType == ProfileSettingType.SETTING) {
            MissionMateTopAppBar(
                navigationType = NavigationType.BACK,
                containerColor = ColorWhite_FFFFFFFF,
                onNavigationClick = { onBackClick?.invoke() }
            )
        }
        Column(
            modifier = modifier
                .padding(bottom = 18.dp)
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = stringResource(id = when (profileSettingType) {
                   ProfileSettingType.CREATE -> R.string.profile_create
                    ProfileSettingType.SETTING -> R.string.profile_setting_title
                }),
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
                        .background(color = it.backgroundColor, shape = CircleShape)
                        .align(Alignment.CenterHorizontally)
                ) {
                    CharacterLargeImage(imageResId = it.imageResId)
                }
            }
            CharacterRow(
                characters = characters,
                onClick = onclickCharacter
            )

            MissionMateTextFieldGroup(
                modifier = modifier
                    .padding(top = 38.dp, start = 24.dp, end = 24.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = nicknameInput,
                onValueChange = { if (regex.matches(it) || it.isEmpty()) nicknameInput = it },
                hintId = R.string.nickname_hint,
                guidanceId = R.string.nickname_input_guide
            )
        }

        MissionMateTextButton(
            modifier = modifier
                .padding(bottom = 36.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
            textId = R.string.save,
            onClick = { onClickSave(nicknameInput) }
        )
    }
}

@Composable
fun CharacterLargeImage(
    modifier: Modifier = Modifier,
    @DrawableRes imageResId: Int,
) {
    Image(
        modifier = modifier
            .padding(10.dp)
            .fillMaxSize(),
        painter = painterResource(id = imageResId),
        contentDescription = ""
    )
}

@Composable
fun CharacterRow(
    modifier: Modifier = Modifier,
    characters: List<CharacterListItem>,
    onClick: (CharacterListItem) -> Unit
) {
    LazyRow(
        modifier = modifier
            .padding(top = 18.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        contentPadding = PaddingValues(horizontal = 24.dp)
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

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        profileSettingType = ProfileSettingType.CREATE,
        characters = listOf(
            CharacterListItem(
                imageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_rabbit_selected,
                nameResId = R.string.rabbit_name,
                isSelected = true,
                backgroundColor = ColorPink_FFFFE4E4
            ),
            CharacterListItem(
                imageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_cat_selected,
                nameResId = R.string.cat_name,
                isSelected = false,
                backgroundColor = ColorPink_FFFFE4E4
            ),
            CharacterListItem(
                imageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_dog_selected,
                nameResId = R.string.dog_name,
                isSelected = false,
                backgroundColor = ColorPink_FFFFE4E4
            ),
            CharacterListItem(
                imageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_panda_selected,
                nameResId = R.string.panda_name,
                isSelected = false,
                backgroundColor = ColorPink_FFFFE4E4
            ),
        ),
        onclickCharacter = {},
        onClickSave = {}
    )
}

@Preview
@Composable
fun CharacterElementPreview() {
    CharacterElement(
        character = CharacterListItem(
            imageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_cat_selected,
            nameResId = R.string.cat_name,
            isSelected = false,
            backgroundColor = ColorPink_FFFFE4E4
        )
    )
}