package com.goalpanzi.mission_mate.core.network

import com.goalpanzi.mission_mate.core.datastore.datasource.AuthDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenInterceptor @Inject constructor(
    private val authDataSource: AuthDataSource
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
        if (response.code == 200) {
            val newAccessToken: String = response.header("Authorization", null) ?: return response
            CoroutineScope(Dispatchers.IO).launch {
                val existedAccessToken = authDataSource.getAccessToken().first()
                if (existedAccessToken != newAccessToken) {
                    authDataSource.setAccessToken(newAccessToken)
                }
            }
        }
        return response
    }
}