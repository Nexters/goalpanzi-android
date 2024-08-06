package com.goalpanzi.mission_mate.core.domain.repository

import com.goalpanzi.mission_mate.core.network.ResultHandler
import com.luckyoct.core.model.GoogleLogin
import com.luckyoct.core.model.base.NetworkResult

interface LoginRepository : ResultHandler {
    suspend fun requestGoogleLogin(email: String): NetworkResult<GoogleLogin>
}