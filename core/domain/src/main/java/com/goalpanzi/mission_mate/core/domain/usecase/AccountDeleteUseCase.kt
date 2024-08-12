package com.goalpanzi.mission_mate.core.domain.usecase

import com.goalpanzi.mission_mate.core.datastore.datasource.DefaultDataSource
import com.goalpanzi.mission_mate.core.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AccountDeleteUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val defaultDataSource: DefaultDataSource
) {
    operator fun invoke() = flow {
        authRepository.requestAccountDelete()
        defaultDataSource.clearUserData().first()
        emit(Unit)
    }
}