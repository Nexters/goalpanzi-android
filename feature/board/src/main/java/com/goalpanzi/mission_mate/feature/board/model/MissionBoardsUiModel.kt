package com.goalpanzi.mission_mate.feature.board.model

import com.goalpanzi.mission_mate.core.domain.model.UserProfile
import com.goalpanzi.mission_mate.core.domain.model.mission.BoardReward
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionBoard
import com.goalpanzi.mission_mate.core.domain.model.mission.MissionBoards

data class MissionBoardsUiModel(
    val missionBoardList : List<MissionBoard>,
    val progressCount : Int,
    val rank : Int,
    val passedCountByMe : Int
){
    val boardRewardList : List<MissionBoard> by lazy {
        missionBoardList.filter { it.reward != BoardReward.NONE }
    }

}

fun MissionBoards.toUiModel() : MissionBoardsUiModel {
    return MissionBoardsUiModel(
        progressCount = progressCount,
        rank = rank,
        missionBoardList = missionBoards,
        passedCountByMe = missionBoards.find { it.isMyPosition }?.number ?: 0
    )
}

fun MissionBoardsUiModel.toBoardPieces(
    profile: UserProfile?
) : List<BoardPiece> {
    return missionBoardList.filter { block ->
        block.missionBoardMembers.isNotEmpty()
    }.map { block ->
        BoardPiece(
            index = block.number,
            count = block.missionBoardMembers.size,
            nickname = if(block.isMyPosition && profile != null) profile.nickname
                       else block.missionBoardMembers.first().nickname,
            isMe = block.isMyPosition,
            drawableRes = if(block.isMyPosition && profile != null) profile.characterType.toCharacterUiModel().imageId
                          else block.missionBoardMembers.first().characterType.toCharacterUiModel().imageId
        )
    }
}