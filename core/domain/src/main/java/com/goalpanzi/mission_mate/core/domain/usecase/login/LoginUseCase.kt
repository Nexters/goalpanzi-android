package com.goalpanzi.mission_mate.core.domain.usecase.login

import com.goalpanzi.mission_mate.core.domain.model.GoogleLogin
import com.goalpanzi.mission_mate.core.domain.model.UserProfile
import com.goalpanzi.mission_mate.core.domain.model.base.DomainResult
import com.goalpanzi.mission_mate.core.domain.repository.AuthRepository
import com.goalpanzi.mission_mate.core.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend fun requestGoogleLogin(email: String): GoogleLogin? {
        return when (val response = authRepository.requestGoogleLogin(email)) {
            is DomainResult.Success -> {
                response.data.also {
                    authRepository.setAccessToken(it.accessToken).first()
                    authRepository.setRefreshToken(it.refreshToken).first()
                    userRepository.setMemberId(it.memberId).first()
                    (it.nickname to it.characterType).let { (nickname, character) ->
                        if (nickname != null && character != null) {
                            userRepository.setUserProfile(
                                UserProfile(nickname, character)
                            ).first()
                        }
                    }
                }
            }

            is DomainResult.Error, is DomainResult.Exception -> null
        }
    }

    fun isNewUser(): Boolean = runBlocking(Dispatchers.IO) {
        authRepository.getAccessToken().first() == null
    }

    fun getCachedUserData(): UserProfile? = runBlocking(Dispatchers.IO) {
        userRepository.getUserProfile().first()
    }
}
