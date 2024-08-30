package com.goalpanzi.mission_mate.feature.board.model
//
//import com.goalpanzi.mission_mate.core.domain.model.mission.BoardReward
//import com.goalpanzi.mission_mate.core.network.model.response.BoardReward
//import com.goalpanzi.mission_mate.core.network.model.response.MissionBoardResponse
//
//data class MissionBoard(
//    val number : Int,
//    val boardReward: BoardReward,
//    val isMyPosition : Boolean,
//    val missionBoardMembers : List<MissionBoardMember>
//)
//
//fun MissionBoardResponse.toModel() : MissionBoard {
//    return MissionBoard(
//        number = number,
//        isMyPosition = isMyPosition,
//        boardReward = reward,
//        missionBoardMembers = missionBoardMembers.map { it.toModel() }
//    )
//}