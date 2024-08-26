package com.goalpanzi.mission_mate.feature.onboarding.model

sealed class JoinResultEvent {
    data class Success(val missionId : Long) : JoinResultEvent()
    data object Error : JoinResultEvent()
}