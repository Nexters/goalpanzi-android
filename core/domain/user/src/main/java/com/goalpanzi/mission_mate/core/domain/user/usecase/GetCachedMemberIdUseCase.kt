package com.goalpanzi.mission_mate.core.domain.user.usecase

import com.goalpanzi.mission_mate.core.domain.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCachedMemberIdUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Long?> = userRepository.getMemberId()
}
