package com.goalpanzi.mission_mate.feature.board.model.uimodel

import com.goalpanzi.mission_mate.core.domain.model.mission.MissionVerifications

sealed class MissionVerificationUiModel {
    data object Loading : MissionVerificationUiModel()
    data object Error : MissionVerificationUiModel()
    data class Success(val missionVerifications: MissionVerifications) : MissionVerificationUiModel()
}