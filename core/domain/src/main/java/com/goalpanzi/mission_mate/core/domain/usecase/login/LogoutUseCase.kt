package com.goalpanzi.mission_mate.core.domain.usecase.login

import com.goalpanzi.mission_mate.core.domain.repository.AuthRepository
import com.goalpanzi.mission_mate.core.domain.repository.DefaultRepository
import com.goalpanzi.mission_mate.core.domain.repository.MissionRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val defaultRepository: DefaultRepository,
    private val missionRepository: MissionRepository
) {
    operator fun invoke() = flow {
        authRepository.requestLogout()
        defaultRepository.clearUserData().first()
        missionRepository.clearMissionData().first()
        emit(Unit)
    }
}