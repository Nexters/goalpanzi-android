package com.goalpanzi.mission_mate.feature.board.model.uimodel

import com.goalpanzi.mission_mate.core.domain.mission.model.MissionDetail


sealed class MissionUiModel {
    data object Loading : MissionUiModel()
    data object Error : MissionUiModel()
    data class Success(val missionDetail: MissionDetail) : MissionUiModel()
}

