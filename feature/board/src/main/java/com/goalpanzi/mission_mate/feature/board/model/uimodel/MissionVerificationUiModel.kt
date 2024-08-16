package com.goalpanzi.mission_mate.feature.board.model.uimodel

import com.luckyoct.core.model.response.MissionVerificationsResponse

sealed class MissionVerificationUiModel {
    data object Loading : MissionVerificationUiModel()
    data object Error : MissionVerificationUiModel()
    data class Success(val missionVerificationsResponse: MissionVerificationsResponse) : MissionVerificationUiModel()
}