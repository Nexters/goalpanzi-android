package com.goalpanzi.mission_mate.core.network

interface TokenExpirationHandler {
    suspend fun handleRefreshTokenExpiration()
}
