package com.goalpanzi.mission_mate.core.data.auth

import com.goalpanzi.mission_mate.core.datastore.datasource.AuthDataSource
import com.goalpanzi.mission_mate.core.datastore.datasource.DefaultDataSource
import com.goalpanzi.mission_mate.core.datastore.datasource.MissionDataSource
import com.goalpanzi.mission_mate.core.navigation.NavigationEventHandler
import com.goalpanzi.mission_mate.core.navigation.di.AuthNavigation
import com.goalpanzi.mission_mate.core.network.TokenExpirationHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class AuthTokenExpirationHandler @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val defaultDataSource: DefaultDataSource,
    private val missionDataSource: MissionDataSource,
    @AuthNavigation private val authNavigationEventHandler: NavigationEventHandler
) : TokenExpirationHandler {

    override suspend fun handleRefreshTokenExpiration() {
        clearCachedData()
        authNavigationEventHandler.triggerRouteToNavigate("RouteModel.Login")
    }

    private suspend fun clearCachedData(){
        authDataSource.setAccessToken("")
        authDataSource.setRefreshToken("")
        defaultDataSource.clearUserData().collect()
        missionDataSource.clearMissionData().collect()
    }
}
