package com.goalpanzi.mission_mate.core.domain.repository

import com.goalpanzi.mission_mate.core.network.ResultHandler
import com.goalpanzi.core.model.response.GoogleLogin
import com.goalpanzi.core.model.base.NetworkResult

interface AuthRepository : ResultHandler {
    suspend fun requestGoogleLogin(email: String): NetworkResult<GoogleLogin>
    suspend fun requestLogout(): NetworkResult<Unit>
    suspend fun requestAccountDelete(): NetworkResult<Unit>
}