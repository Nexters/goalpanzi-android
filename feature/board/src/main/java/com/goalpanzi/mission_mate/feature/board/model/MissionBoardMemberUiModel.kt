package com.goalpanzi.mission_mate.feature.board.model

import com.goalpanzi.mission_mate.core.domain.mission.model.MissionBoardMembers

data class MissionBoardMemberUiModel(
    val nickname : String,
    val characterUiModel : CharacterUiModel,
 //   val memberId : Long
)

fun MissionBoardMembers.toModel() : MissionBoardMemberUiModel {
    return MissionBoardMemberUiModel(
        nickname = nickname,
        characterUiModel = characterType.toCharacterUiModel(),
       // memberId = memberId
    )
}
