package com.luckyoct.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorBlue_FFBFD7FF
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorLightBlue_FFBCE7FF
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorLightBrown_FFF7D8B3
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorLightGreen_FFC2E792
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorLightYellow_FFFFE59A
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorPink_FFFFE4E4
import com.goalpanzi.mission_mate.core.domain.usecase.ProfileUseCase
import com.luckyoct.core.model.base.NetworkResult
import com.luckyoct.feature.profile.model.CharacterListItem
import com.luckyoct.feature.profile.model.ProfileEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel @AssistedInject constructor(
    @Assisted private val profileSettingType: ProfileSettingType,
    private val profileUseCase: ProfileUseCase
): ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(profileSettingType: ProfileSettingType): ProfileViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            assistedFactory: Factory,
            profileSettingType: ProfileSettingType
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(profileSettingType) as T
            }
        }
    }

    private val defaultImageIds = listOf(
        com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_rabbit_selected,
        com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_cat_selected,
        com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_dog_selected,
        com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_panda_selected,
        com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_bear_selected,
        com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_bird_selected
    )
    private val defaultNameIds = listOf(
        R.string.rabbit_name,
        R.string.cat_name,
        R.string.dog_name,
        R.string.panda_name,
        R.string.bear_name,
        R.string.bird_name
    )
    private val defaultColors = listOf(
        ColorPink_FFFFE4E4,
        ColorBlue_FFBFD7FF,
        ColorLightYellow_FFFFE59A,
        ColorLightGreen_FFC2E792,
        ColorLightBrown_FFF7D8B3,
        ColorLightBlue_FFBCE7FF
    )

    private val _event = MutableSharedFlow<ProfileEvent>()
    val event = _event.asSharedFlow()

    private val _characters = MutableStateFlow(
        when (profileSettingType) {
            ProfileSettingType.CREATE -> {
                defaultImageIds.indices.map {
                    CharacterListItem(
                        imageResId = defaultImageIds[it],
                        nameResId = defaultNameIds[it],
                        isSelected = it == 0,
                        backgroundColor = defaultColors[it]
                    )
                }
            }
            // TODO : set my character selected
            ProfileSettingType.SETTING -> {
                defaultImageIds.indices.map {
                    CharacterListItem(
                        imageResId = defaultImageIds[it],
                        nameResId = defaultNameIds[it],
                        isSelected = false,
                        backgroundColor = defaultColors[it]
                    )
                }
            }
        }
    )
    val characters = _characters.asStateFlow()

    fun selectCharacter(character: CharacterListItem) {
        _characters.value = _characters.value.map {
            it.copy(isSelected = it == character)
        }
    }

    fun saveProfile(nickname: String) {
        if (nickname.isEmpty()) return

        viewModelScope.launch {
            val response = profileUseCase.saveProfile(nickname, characters.value.indexOfFirst { it.isSelected })
            when (response) {
                is NetworkResult.Success -> {
                    _event.emit(ProfileEvent.Success)
                }
                else -> return@launch
            }
        }
    }
}