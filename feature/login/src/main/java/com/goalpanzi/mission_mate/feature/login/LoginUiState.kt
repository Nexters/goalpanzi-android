package com.goalpanzi.mission_mate.feature.login

sealed interface LoginUiState {
    data object Loading : LoginUiState
    data class Success(val isAlreadyMember: Boolean) : LoginUiState
}