package com.goalpanzi.mission_mate.core.domain.auth.usecase

import com.goalpanzi.mission_mate.core.domain.auth.repository.AuthRepository
import com.goalpanzi.mission_mate.core.domain.mission.repository.MissionRepository
import com.goalpanzi.mission_mate.core.domain.user.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val missionRepository: MissionRepository
) {
    operator fun invoke() = flow {
        authRepository.requestLogout()
        userRepository.clearUserData().first()
        missionRepository.clearMissionData().first()
        emit(Unit)
    }
}
