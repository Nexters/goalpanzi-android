package com.goalpanzi.mission_mate.core.domain.usecase

import com.goalpanzi.mission_mate.core.domain.repository.LoginRepository
import com.luckyoct.core.model.GoogleLogin
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend fun requestGoogleLogin(token: String, email: String): GoogleLogin = loginRepository.requestGoogleLogin(token, email)

}