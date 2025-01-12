package com.goalpanzi.mission_mate.feature.main.ext

import androidx.navigation.NavDestination
import com.goalpanzi.mission_mate.core.navigation.RouteModel
import com.goalpanzi.mission_mate.core.navigation.fullPathName

fun NavDestination?.compareTo(routeModel: RouteModel) : Boolean {
    return this?.route == routeModel.fullPathName()
}

private val darkStatusBarScreenRouteNames = setOf(
    RouteModel.BoardRouteModel.UserStory::class.qualifiedName,
    RouteModel.BoardRouteModel.VerificationPreview::class.qualifiedName
)

fun NavDestination?.isDarkStatusBarScreen() : Boolean {
    if(this?.route == null) return false
    return darkStatusBarScreenRouteNames.any { name ->
        route?.contains(name ?: return false) == true
    }
}

