package com.goalpanzi.mission_mate.feature.setting

sealed interface Event {
    data object GoToLogin : Event
}