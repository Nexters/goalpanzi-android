package com.goalpanzi.mission_mate.core.navigation

import kotlinx.coroutines.flow.SharedFlow

interface NavigationEventHandler {
    val routeToNavigate : SharedFlow<RouteModel>

    suspend fun triggerRouteToNavigate(route : RouteModel)
}
