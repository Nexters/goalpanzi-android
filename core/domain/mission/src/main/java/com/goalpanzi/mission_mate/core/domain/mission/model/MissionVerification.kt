package com.goalpanzi.mission_mate.core.domain.mission.model

import com.goalpanzi.mission_mate.core.domain.common.model.user.CharacterType

data class MissionVerification(
    val nickname : String,
    val characterType : CharacterType = CharacterType.RABBIT,
    val imageUrl : String = "",
    val verifiedAt : String = ""
)
