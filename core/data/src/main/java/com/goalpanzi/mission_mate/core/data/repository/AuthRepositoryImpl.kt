package com.goalpanzi.mission_mate.core.data.repository

import com.goalpanzi.mission_mate.core.domain.repository.AuthRepository
import com.goalpanzi.mission_mate.core.network.service.LoginService
import com.goalpanzi.core.model.base.NetworkResult
import com.goalpanzi.core.model.request.GoogleLoginRequest
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val loginService: LoginService
): AuthRepository {

    override suspend fun requestGoogleLogin(email: String) = handleResult {
        val request = GoogleLoginRequest(email = email)
        loginService.requestGoogleLogin(request)
    }

    override suspend fun requestLogout(): NetworkResult<Unit> = handleResult {
        loginService.requestLogout()
    }

    override suspend fun requestAccountDelete(): NetworkResult<Unit> = handleResult {
        loginService.requestDeleteAccount()
    }
}