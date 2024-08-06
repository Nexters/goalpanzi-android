package com.goalpanzi.mission_mate.core.data.repository

import com.goalpanzi.mission_mate.core.domain.repository.LoginRepository
import com.goalpanzi.mission_mate.core.network.service.LoginService
import com.luckyoct.core.model.GoogleLogin
import com.luckyoct.core.model.base.NetworkResult
import com.luckyoct.core.model.request.GoogleLoginRequest
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginService: LoginService
): LoginRepository {

    override suspend fun requestGoogleLogin(email: String) = handleResult {
        val request = GoogleLoginRequest(email = email)
        loginService.requestGoogleLogin(request)
    }
}