package com.goalpanzi.mission_mate.feature.board.model

import com.goalpanzi.core.model.UserProfile
import com.goalpanzi.core.model.response.BoardReward
import com.goalpanzi.core.model.response.MissionBoardsResponse

data class MissionBoards(
    val missionBoardList : List<MissionBoard>,
    val progressCount : Int,
    val rank : Int,
    val passedCountByMe : Int
){
    val boardRewardList : List<MissionBoard> by lazy {
        missionBoardList.filter { it.boardReward != BoardReward.NONE }
    }

//    val passedCountByMe : Int
//        get() {
//            return missionBoardList.find { it.isMyPosition }?.number ?: 0
//        }
}

fun MissionBoardsResponse.toModel() : MissionBoards {
    return MissionBoards(
        progressCount = progressCount,
        rank = rank,
        missionBoardList = missionBoards.map { it.toModel() },
        passedCountByMe = missionBoards.find { it.isMyPosition }?.number ?: 0
    )
}

fun MissionBoards.toBoardPieces(
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
            drawableRes = if(block.isMyPosition && profile != null) profile.characterType.toCharacter().imageId
                          else block.missionBoardMembers.first().character.imageId
        )
    }
}