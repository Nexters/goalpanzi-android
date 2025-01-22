package com.goalpanzi.mission_mate.core.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class AuthNavigationEventHandler @Inject constructor() : NavigationEventHandler {

    private val _routeToNavigate = MutableSharedFlow<RouteModel>()
    override val routeToNavigate: SharedFlow<RouteModel> = _routeToNavigate.asSharedFlow()

    override suspend fun triggerRouteToNavigate(route: RouteModel) {
        _routeToNavigate.emit(route)
    }
}
