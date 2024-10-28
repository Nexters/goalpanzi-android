package com.goalpanzi.mission_mate.feature.board.model.uimodel

import com.goalpanzi.mission_mate.feature.board.model.MissionBoardsUiModel

sealed class MissionBoardUiModel {
    data object Loading : MissionBoardUiModel()
    data object Error : MissionBoardUiModel()
    data class Success(val missionBoards : MissionBoardsUiModel) : MissionBoardUiModel()
}
