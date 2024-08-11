package com.goalpanzi.mission_mate.core.domain.usecase

import com.goalpanzi.mission_mate.core.datastore.datasource.AuthDataSource
import com.goalpanzi.mission_mate.core.domain.repository.AuthRepository
import com.luckyoct.core.model.GoogleLogin
import com.luckyoct.core.model.base.NetworkResult
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val authDataSource: AuthDataSource
) {
    suspend fun requestGoogleLogin(email: String): GoogleLogin? {
        return when (val response = authRepository.requestGoogleLogin(email)) {
            is NetworkResult.Success -> {
                response.data.also {
                    authDataSource.setAccessToken(it.accessToken).first()
                    authDataSource.setRefreshToken(it.refreshToken).first()
                }
            }
            is NetworkResult.Error, is NetworkResult.Exception -> null
        }
    }

}