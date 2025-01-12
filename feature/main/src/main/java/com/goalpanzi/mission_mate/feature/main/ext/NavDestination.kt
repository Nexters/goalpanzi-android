package com.goalpanzi.mission_mate.feature.main.ext

import androidx.navigation.NavDestination
import com.goalpanzi.mission_mate.core.navigation.RouteModel
import com.goalpanzi.mission_mate.core.navigation.fullPathName

fun NavDestination?.compareTo(routeModel: RouteModel) : Boolean {
    return this?.route == routeModel.fullPathName()
}

