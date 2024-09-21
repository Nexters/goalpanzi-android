package com.goalpanzi.mission_mate.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.goalpanzi.mission_mate.core.domain.common.DomainResult
import com.goalpanzi.mission_mate.core.domain.common.model.user.CharacterType
import com.goalpanzi.mission_mate.core.domain.common.model.user.UserProfile
import com.goalpanzi.mission_mate.core.domain.user.usecase.ProfileUseCase
import com.goalpanzi.mission_mate.feature.profile.model.CharacterListItem
import com.goalpanzi.mission_mate.feature.profile.model.ProfileUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _isNicknameDuplicated = MutableStateFlow(false)
    val isNicknameDuplicated = _isNicknameDuplicated.asStateFlow()

    private val _isNotChangedProfileInput = MutableStateFlow(true)
    val isNotChangedProfileInput: StateFlow<Boolean> = _isNotChangedProfileInput.asStateFlow()

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
                    _isNotChangedProfileInput.emit(false)
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
                    _isNotChangedProfileInput.emit(true)
                }
            }
        }
    }

//    fun updateNickname(input: String) {
//        viewModelScope.launch {
//            _invalidNicknameError.emit(
//                if (input.length > 6) {
//                    InvalidNicknameError.TooLong
//                } else if (input.contains(Regex("[^가-힣a-zA-Z0-9]"))) {
//                    InvalidNicknameError.IncludeSpecial
//                } else {
//                    InvalidNicknameError.Duplicated
//                }
//            )
//        }
//    }

    fun selectCharacter(character: CharacterListItem) {
        val state = uiState.value as? ProfileUiState.Success ?: return
        _uiState.value = state.copy(
            characterList = state.characterList.map {
                it.copy(isSelected = it == character)
            }
        )
        viewModelScope.launch {
            _isNotChangedProfileInput.emit(profileUseCase.getProfile()?.characterType == character.type)
        }

    }

    fun resetNicknameErrorState() {
        _isNicknameDuplicated.value = false
    }

    fun saveProfile(nickname: String) {
        if (nickname.isEmpty()) return
        viewModelScope.launch {
            val selectedItem = (uiState.value as? ProfileUiState.Success)?.characterList?.find {
                it.isSelected
            } ?: return@launch

            when (
                val response = profileUseCase
                    .saveProfile(
                        nickname = nickname,
                        type = selectedItem.type,
                        isEqualNickname = profileUseCase.getProfile()?.nickname == nickname
                    )
            ) {
                is DomainResult.Success -> {
                    _isSaveSuccess.emit(true)
                }

                is DomainResult.Exception -> {}
                is DomainResult.Error -> {
                    if (response.code == 409) {
                        _isNicknameDuplicated.emit(true)
                    }
                }
            }
        }
    }
}
