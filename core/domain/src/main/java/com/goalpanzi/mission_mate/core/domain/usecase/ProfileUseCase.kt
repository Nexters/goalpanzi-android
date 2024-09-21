package com.goalpanzi.mission_mate.core.domain.usecase

import com.goalpanzi.mission_mate.core.domain.model.CharacterType
import com.goalpanzi.mission_mate.core.domain.model.UserProfile
import com.goalpanzi.mission_mate.core.domain.model.base.DomainResult
import com.goalpanzi.mission_mate.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun saveProfile(nickname: String, type: CharacterType, isEqualNickname: Boolean) =
        userRepository.saveProfile(nickname, type, isEqualNickname).also {
            if (it is DomainResult.Success) {
                userRepository.setUserProfile(UserProfile(nickname, type)).first()
            }
        }

    suspend fun getProfile(): UserProfile? = userRepository.getUserProfile().first()
}
