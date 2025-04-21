package com.goalpanzi.mission_mate.core.domain.mission.model

import com.goalpanzi.mission_mate.core.domain.common.model.user.CharacterType
import java.time.LocalDate

data class VerificationInfoByBlockNumber(
    val nickname: String,
    val characterType: CharacterType,
    val imageUrl: String,
    val verifiedAt: LocalDate
)