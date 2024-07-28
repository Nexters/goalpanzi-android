package com.goalpanzi.mission_mate.core.data.repository

import com.goalpanzi.mission_mate.core.domain.repository.LoginRepository
import com.goalpanzi.mission_mate.core.network.service.LoginService
import com.luckyoct.core.model.GoogleLogin
import com.luckyoct.core.model.request.GoogleLoginRequest
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginService
): LoginRepository {

    override suspend fun requestGoogleLogin(token: String, email: String): GoogleLogin {
        val request = GoogleLoginRequest(identityToken = token, email = email)
        val response = loginService.requestGoogleLogin(request)
        return response
    }
}