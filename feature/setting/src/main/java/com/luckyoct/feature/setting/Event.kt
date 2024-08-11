package com.luckyoct.feature.setting

sealed interface Event {
    data object GoToLogin : Event
}