package com.goalpanzi.mission_mate.feature.board.model

import com.goalpanzi.mission_mate.core.domain.common.model.user.UserProfile
import com.goalpanzi.mission_mate.core.domain.mission.model.BoardReward
import com.goalpanzi.mission_mate.core.domain.mission.model.MissionBoard
import com.goalpanzi.mission_mate.core.domain.mission.model.MissionBoards

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
    return missionBoardList.map { block ->
        block.missionBoardMembers.mapIndexed { index,  member ->
            BoardPiece(
                memberId = member.memberId,
                index = block.number,
                count = block.missionBoardMembers.size,
                nickname = member.nickname,
                isMe = block.isMyPosition,
                drawableRes = member.characterType.toCharacterUiModel().imageId,
                boardPieceType = if(block.missionBoardMembers.firstOrNull()?.memberId == member.memberId) BoardPieceType.INITIAL
                                else BoardPieceType.HIDDEN,
                order = index
            )
        }.reversed()
    }.flatten()
}
