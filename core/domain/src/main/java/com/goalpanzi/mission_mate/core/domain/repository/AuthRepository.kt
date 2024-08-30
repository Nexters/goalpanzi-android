package com.goalpanzi.mission_mate.core.domain.repository

import com.goalpanzi.mission_mate.core.domain.model.GoogleLogin
import com.goalpanzi.mission_mate.core.domain.model.base.DomainResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun requestGoogleLogin(email: String): DomainResult<GoogleLogin>
    suspend fun requestLogout(): DomainResult<Unit>
    suspend fun requestAccountDelete(): DomainResult<Unit>

    fun getAccessToken() : Flow<String?>
    fun getRefreshToken() : Flow<String?>

    fun setAccessToken(accessToken : String) : Flow<Unit>
    fun setRefreshToken(refreshToken : String) : Flow<Unit>
}