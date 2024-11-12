package com.goalpanzi.mission_mate.core.domain.mission.model

import com.goalpanzi.mission_mate.core.domain.common.model.user.CharacterType

data class MissionBoardMembers(
    val memberId : Long,
    val nickname : String,
    val characterType : CharacterType = CharacterType.RABBIT
)
