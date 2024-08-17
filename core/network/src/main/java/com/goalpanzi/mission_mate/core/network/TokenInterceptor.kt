package com.goalpanzi.mission_mate.core.network

import com.goalpanzi.mission_mate.core.datastore.datasource.AuthDataSource
import com.goalpanzi.mission_mate.core.network.service.TokenService
import com.luckyoct.core.model.request.TokenReissueRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val tokenService: TokenService
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder().apply {
            runBlocking {
                val token = authDataSource.getAccessToken().first()
                token?.let {
                    addHeader("Authorization", "Bearer $it")
                }
            }
        }

        val response = chain.proceed(newRequest.build())

        when (response.code) {
            HttpURLConnection.HTTP_OK -> {
                val newAccessToken: String = response.header("Authorization", null) ?: return response
                CoroutineScope(Dispatchers.IO).launch {
                    val existedAccessToken = authDataSource.getAccessToken().first()
                    if (existedAccessToken != newAccessToken) {
                        authDataSource.setAccessToken(newAccessToken)
                    }
                }
            }
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                val retryRequest = chain.request().newBuilder().apply {
                    runBlocking {
                        authDataSource.getRefreshToken().first()?.let {
                            val newToken = tokenService.requestTokenReissue(TokenReissueRequest(it))
                            if (newToken.isSuccessful) {
                                newToken.body()?.let { token ->
                                    addHeader("Authorization", "Bearer ${token.accessToken}")
                                    CoroutineScope(Dispatchers.IO).launch {
                                        authDataSource.setAccessToken(token.accessToken)
                                        authDataSource.setRefreshToken(token.refreshToken)
                                    }
                                }
                            }
                        }
                    }
                }
                return chain.proceed(retryRequest.build())
            }
        }
        return response
    }
}