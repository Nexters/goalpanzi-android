package com.goalpanzi.mission_mate.feature.board.model.uimodel

import com.luckyoct.core.model.response.MissionDetailResponse

sealed class MissionUiModel {
    data object Loading : MissionUiModel()
    data object Error : MissionUiModel()
    data class Success(val missionDetailResponse: MissionDetailResponse) : MissionUiModel()
}