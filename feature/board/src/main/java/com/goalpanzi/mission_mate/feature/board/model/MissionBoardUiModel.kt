package com.goalpanzi.mission_mate.feature.board.model

import com.luckyoct.core.model.response.MissionBoardsResponse

sealed class MissionBoardUiModel {
    data object Loading : MissionBoardUiModel()
    data object Error : MissionBoardUiModel()
    data class Success(val missionBoardsResponse: MissionBoardsResponse) : MissionBoardUiModel()
}