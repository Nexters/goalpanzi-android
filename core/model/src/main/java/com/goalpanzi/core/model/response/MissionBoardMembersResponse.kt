package com.goalpanzi.core.model.response

import com.goalpanzi.core.model.CharacterType
import kotlinx.serialization.Serializable

@Serializable
data class MissionBoardMembersResponse(
    //val memberId : Long,
    val nickname : String,
    val characterType : CharacterType = CharacterType.RABBIT
)
