package com.goalpanzi.mission_mate.core.domain.usecase

import com.goalpanzi.mission_mate.core.domain.model.CharacterType
import com.goalpanzi.mission_mate.core.domain.model.UserProfile
import com.goalpanzi.mission_mate.core.domain.model.base.DomainResult
import com.goalpanzi.mission_mate.core.domain.repository.DefaultRepository
import com.goalpanzi.mission_mate.core.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val defaultRepository: DefaultRepository
) {
    suspend fun saveProfile(nickname: String, type: CharacterType, isEqualNickname: Boolean) =
        profileRepository.saveProfile(nickname, type, isEqualNickname).also {
            if (it is DomainResult.Success) {
                defaultRepository.setUserProfile(UserProfile(nickname, type)).first()
            }
        }

    suspend fun getProfile(): UserProfile? = defaultRepository.getUserProfile().first()
}