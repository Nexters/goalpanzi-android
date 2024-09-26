package com.goalpanzi.mission_mate.core.data.auth

import com.goalpanzi.mission_mate.core.datastore.datasource.AuthDataSource
import com.goalpanzi.mission_mate.core.network.TokenProvider
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AuthTokenProvider @Inject constructor(
    private val authDataSource: AuthDataSource
) : TokenProvider {
    override suspend fun getAccessToken(): String? {
        return authDataSource.getAccessToken().firstOrNull()
    }

    override suspend fun getRefreshToken(): String? {
        return authDataSource.getRefreshToken().firstOrNull()
    }

    override suspend fun setAccessToken(accessToken: String) {
        authDataSource.setAccessToken(accessToken)
    }

    override suspend fun setRefreshToken(refreshToken: String) {
        authDataSource.setRefreshToken(refreshToken)
    }


}
