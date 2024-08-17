package com.goalpanzi.mission_mate.core.network.service

import com.luckyoct.core.model.request.TokenReissueRequest
import com.luckyoct.core.model.response.TokenReissue
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenService {
    @POST("/api/auth/token:reissue")
    suspend fun requestTokenReissue(
        @Body request: TokenReissueRequest
    ): Response<TokenReissue>
}