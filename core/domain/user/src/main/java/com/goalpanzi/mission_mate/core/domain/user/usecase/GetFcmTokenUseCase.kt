package com.goalpanzi.mission_mate.core.domain.user.usecase

import com.goalpanzi.mission_mate.core.domain.user.repository.UserRepository
import javax.inject.Inject

class GetFcmTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke() = userRepository.getFcmToken()
}

