package com.goalpanzi.mission_mate.core.datastore.datasource

import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    fun getAccessToken() : Flow<String?>
    fun getRefreshToken() : Flow<String?>

    fun setAccessToken(accessToken : String) : Flow<Unit>
    fun setRefreshToken(refreshToken : String) : Flow<Unit>
}