package com.goalpanzi.mission_mate.core.domain.usecase

import com.goalpanzi.mission_mate.core.datastore.datasource.DefaultDataSource
import com.goalpanzi.mission_mate.core.datastore.datasource.MissionDataSource
import com.goalpanzi.mission_mate.core.domain.repository.AuthRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val defaultDataSource: DefaultDataSource,
    private val missionDataSource: MissionDataSource
) {
    operator fun invoke() = flow {
        authRepository.requestLogout()
        defaultDataSource.clearUserData().first()
        missionDataSource.clearMissionData().first()
        emit(Unit)
    }
}