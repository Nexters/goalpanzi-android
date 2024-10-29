package com.goalpanzi.mission_mate.core.navigation

import kotlinx.coroutines.flow.SharedFlow

interface NavigationEventHandler {
    val routeToNavigate : SharedFlow<String>

    suspend fun triggerRouteToNavigate(route : String)
}
