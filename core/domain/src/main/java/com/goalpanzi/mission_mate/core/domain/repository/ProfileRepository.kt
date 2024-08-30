package com.goalpanzi.mission_mate.core.domain.repository

import com.goalpanzi.mission_mate.core.domain.model.CharacterType
import com.goalpanzi.mission_mate.core.domain.model.base.DomainResult

interface ProfileRepository {
    suspend fun saveProfile(nickname: String, type: CharacterType, isEqualNickname : Boolean): DomainResult<Unit>
}