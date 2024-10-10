package com.goalpanzi.mission_mate.core.data.auth

import com.goalpanzi.mission_mate.core.datastore.datasource.AuthDataSource
import com.goalpanzi.mission_mate.core.network.TokenExpirationHandler
import javax.inject.Inject

class AuthTokenExpirationHandler @Inject constructor(

) : TokenExpirationHandler {
    override fun handleRefreshTokenExpiration() {

    }
}
