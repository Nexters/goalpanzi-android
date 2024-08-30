package com.goalpanzi.mission_mate.core.domain.model.mission

import com.goalpanzi.mission_mate.core.domain.model.CharacterType

data class MissionBoardMembers(
    val nickname : String,
    val characterType : CharacterType = CharacterType.RABBIT
)
