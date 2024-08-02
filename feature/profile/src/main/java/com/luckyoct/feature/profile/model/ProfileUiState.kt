package com.luckyoct.feature.profile.model

sealed interface ProfileEvent {

    data object Loading : ProfileEvent

    data object Success : ProfileEvent
}