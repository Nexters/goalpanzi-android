package com.luckyoct.feature.profile.model

sealed interface ProfileUiState {

    data object Loading : ProfileUiState

    data class Success(
        val nickname: String,
        val characterList: List<CharacterListItem>
    ) : ProfileUiState
}