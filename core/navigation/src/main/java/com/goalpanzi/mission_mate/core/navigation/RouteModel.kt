package com.goalpanzi.mission_mate.core.navigation

import kotlinx.serialization.Serializable

sealed interface RouteModel {
    @Serializable
    data object Login : RouteModel
}