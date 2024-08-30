package com.goalpanzi.mission_mate.core.domain.usecase

import com.goalpanzi.mission_mate.core.domain.repository.AuthRepository
import com.goalpanzi.mission_mate.core.domain.repository.DefaultRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AccountDeleteUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val defaultRepository: DefaultRepository
) {
    operator fun invoke() = flow {
        authRepository.requestAccountDelete()
        defaultRepository.clearUserData().first()
        emit(Unit)
    }
}