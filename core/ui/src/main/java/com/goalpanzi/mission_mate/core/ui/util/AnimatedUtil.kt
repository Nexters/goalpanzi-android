package com.goalpanzi.mission_mate.core.ui.util

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideInFromLeft(
    durationMills: Int = 300
): EnterTransition {
    return slideIntoContainer(
        towards = SlideDirection.Left,
        animationSpec = tween(durationMills)
    )
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideOutToEnd(
    durationMills: Int = 300
): ExitTransition {
    return slideOutOfContainer(
        towards = SlideDirection.End,
        animationSpec = tween(durationMills)
    )
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideInToUp(
    durationMills: Int = 300
): EnterTransition {
    return slideIntoContainer(
        towards = SlideDirection.Up,
        animationSpec = tween(durationMills)
    )
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideOutToDown(
    durationMills: Int = 300
): ExitTransition {
    return slideOutOfContainer(
        towards = SlideDirection.Down,
        animationSpec = tween(durationMills)
    )
}

