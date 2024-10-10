package com.goalpanzi.mission_mate.core.network.interceptor

import com.goalpanzi.mission_mate.core.network.TokenProvider
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenReissueInterceptor @Inject constructor(
    private val tokenProvider: TokenProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder().apply {
            runBlocking {
                val token = tokenProvider.getAccessToken()
                token?.let {
                    addHeader("Authorization", "Bearer $it")
                }
            }
        }
        val response = chain.proceed(newRequest.build())

        when (response.code) {
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                return chain.proceed(newRequest.build())
            }
        }
        return chain.proceed(newRequest.build())
    }
}
