package com.goalpanzi.mission_mate.core.network.service

import com.goalpanzi.core.model.response.GoogleLogin
import com.goalpanzi.core.model.request.GoogleLoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface LoginService {

    @POST("/api/auth/login/google")
    suspend fun requestGoogleLogin(
        @Body request: GoogleLoginRequest
    ): Response<GoogleLogin>

    @POST("/api/auth/logout")
    suspend fun requestLogout(): Response<Unit>

    @DELETE("/api/member")
    suspend fun requestDeleteAccount(): Response<Unit>
}