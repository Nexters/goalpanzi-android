package com.goalpanzi.mission_mate.feature.login

sealed interface LoginEvent {
    data object Error : LoginEvent
    data class Success(val isAlreadyMember: Boolean) : LoginEvent
}