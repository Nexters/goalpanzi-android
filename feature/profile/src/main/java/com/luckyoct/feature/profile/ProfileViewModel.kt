package com.luckyoct.feature.profile

import android.util.Log
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
import com.luckyoct.core.model.CharacterType
import com.luckyoct.core.model.UserProfile
import com.luckyoct.core.model.base.NetworkResult
import com.luckyoct.feature.profile.model.CharacterListItem
import com.luckyoct.feature.profile.model.ProfileUiState
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
) : ViewModel() {

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

    private val defaultCharacters = CharacterListItem.createDefaultList()

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _isInvalidNickname = MutableStateFlow(false)
    val isInvalidNickname = _isInvalidNickname.asStateFlow()

    private val _isSaveSuccess = MutableSharedFlow<Boolean>()
    val isSaveSuccess = _isSaveSuccess.asSharedFlow()

    init {
        viewModelScope.launch {
            when (profileSettingType) {
                ProfileSettingType.CREATE -> {
                    _uiState.value = ProfileUiState.Success(
                        nickname = "",
                        characterList = defaultCharacters.toMutableList().apply {
                            set(0, get(0).copy(isSelected = true))
                        }
                    )
                }

                ProfileSettingType.SETTING -> {
                    val userProfile = profileUseCase.getProfile()
                        ?: UserProfile(
                            nickname = "",
                            characterType = CharacterType.RABBIT
                        ) // TODO : API
                    _uiState.value = ProfileUiState.Success(
                        nickname = userProfile.nickname,
                        characterList = defaultCharacters.toMutableList().apply {
                            val index = indexOfFirst { it.type == userProfile.characterType }
                            set(index, get(index).copy(isSelected = true))
                        }
                    )
                }
            }
        }
    }

    fun selectCharacter(character: CharacterListItem) {
        val state = uiState.value as? ProfileUiState.Success ?: return
        _uiState.value = state.copy(
            characterList = state.characterList.map {
                it.copy(isSelected = it == character)
            }
        )
    }

    fun resetNicknameErrorState() {
        _isInvalidNickname.value = false
    }

    fun saveProfile(nickname: String) {
        if (nickname.isEmpty()) return
        viewModelScope.launch {
            val selectedItem = (uiState.value as? ProfileUiState.Success)?.characterList?.find {
                it.isSelected
            } ?: return@launch

            when(val response = profileUseCase.saveProfile(nickname, selectedItem.type)) {
                is NetworkResult.Success -> {
                    _isSaveSuccess.emit(true)
                }
                is NetworkResult.Exception -> {}
                is NetworkResult.Error -> {
                    if (response.code == 409) {
                        _isInvalidNickname.emit(true)
                    }
                }
            }
        }
    }
}