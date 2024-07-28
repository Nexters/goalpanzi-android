package com.goalpanzi.mission_mate.core.domain.repository

import com.luckyoct.core.model.GoogleLogin

interface LoginRepository {
    suspend fun requestGoogleLogin(token: String, email: String): GoogleLogin
}