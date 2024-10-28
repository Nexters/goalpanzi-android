package com.goalpanzi.mission_mate.core.domain.auth.usecase

import com.goalpanzi.mission_mate.core.domain.auth.repository.AuthRepository
import com.goalpanzi.mission_mate.core.domain.user.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AccountDeleteUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke() = flow {
        authRepository.requestAccountDelete()
        userRepository.clearUserData().first()
        emit(Unit)
    }
}
