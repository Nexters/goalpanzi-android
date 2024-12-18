package com.goalpanzi.mission_mate.core.domain.user.usecase

import com.goalpanzi.mission_mate.core.domain.user.repository.UserRepository
import javax.inject.Inject

class UpdateFcmTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(fcmToken: String) = userRepository.updateFcmToken(fcmToken)
}
