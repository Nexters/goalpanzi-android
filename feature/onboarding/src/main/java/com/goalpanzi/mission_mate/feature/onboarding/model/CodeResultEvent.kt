package com.goalpanzi.mission_mate.feature.onboarding.model

sealed class CodeResultEvent {
    data class Success(val mission : MissionUiModel) : CodeResultEvent()
    data object Error : CodeResultEvent()
}