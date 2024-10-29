package com.goalpanzi.mission_mate.core.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class AuthNavigationEventHandler @Inject constructor() : NavigationEventHandler {

    private val _routeToNavigate = MutableSharedFlow<String>()
    override val routeToNavigate: SharedFlow<String> = _routeToNavigate.asSharedFlow()

    override suspend fun triggerRouteToNavigate(route: String) {
        _routeToNavigate.emit(route)
    }
}
