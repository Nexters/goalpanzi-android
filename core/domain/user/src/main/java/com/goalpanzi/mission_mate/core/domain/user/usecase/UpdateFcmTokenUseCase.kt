package com.goalpanzi.mission_mate.core.domain.user.usecase

import com.goalpanzi.mission_mate.core.domain.user.repository.UserRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class UpdateFcmTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(fcmToken: String) {
        val cachedFcmToken = userRepository.getCachedFcmToken().firstOrNull()
        if(fcmToken != cachedFcmToken){
            userRepository.setCachedFcmToken(fcmToken).collect()
            userRepository.updateFcmToken(fcmToken)
        }
    }
}
