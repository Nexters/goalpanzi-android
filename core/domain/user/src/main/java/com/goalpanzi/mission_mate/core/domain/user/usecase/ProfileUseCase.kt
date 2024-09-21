package com.goalpanzi.mission_mate.core.domain.user.usecase

import com.goalpanzi.mission_mate.core.domain.common.DomainResult
import com.goalpanzi.mission_mate.core.domain.common.model.user.CharacterType
import com.goalpanzi.mission_mate.core.domain.common.model.user.UserProfile
import com.goalpanzi.mission_mate.core.domain.user.repository.UserRepository
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
