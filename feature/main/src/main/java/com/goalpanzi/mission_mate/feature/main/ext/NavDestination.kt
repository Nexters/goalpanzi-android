package com.goalpanzi.mission_mate.feature.main.ext

import androidx.navigation.NavDestination
import com.goalpanzi.mission_mate.core.navigation.model.RouteModel
import com.goalpanzi.mission_mate.core.navigation.model.fullPathName

fun NavDestination?.compareTo(routeModel: RouteModel) : Boolean {
    return this?.route == routeModel.fullPathName()
}

private val darkStatusBarScreenRouteNames = setOf(
    RouteModel.MainTabRoute.MissionRouteModel.UserStory::class.qualifiedName,
    RouteModel.MainTabRoute.MissionRouteModel.VerificationPreview::class.qualifiedName
)

fun NavDestination?.isDarkStatusBarScreen() : Boolean {
    if(this?.route == null) return false
    return darkStatusBarScreenRouteNames.any { name ->
        route?.contains(name ?: return false) == true
    }
}

