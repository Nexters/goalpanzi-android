package com.goalpanzi.mission_mate.core.navigation

import com.goalpanzi.mission_mate.core.navigation.model.RouteModel
import kotlinx.coroutines.flow.SharedFlow

interface NavigationEventHandler {
    val routeToNavigate : SharedFlow<RouteModel>

    suspend fun triggerRouteToNavigate(route : RouteModel)
}
