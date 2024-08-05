package com.goalpanzi.mission_mate.core.domain.usecase

import com.goalpanzi.mission_mate.core.datastore.datasource.AuthDataSource
import com.goalpanzi.mission_mate.core.domain.repository.LoginRepository
import com.luckyoct.core.model.GoogleLogin
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val authDataSource: AuthDataSource
) {

    suspend fun requestGoogleLogin(token: String, email: String): GoogleLogin {
        val response = loginRepository.requestGoogleLogin(token, email)
        authDataSource.setAccessToken(response.accessToken).first()
        authDataSource.setRefreshToken(response.refreshToken).first()
        return response
    }

}