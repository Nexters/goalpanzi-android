package com.goalpanzi.mission_mate.core.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.goalpanzi.mission_mate.core.main.component.MainNavHost
import com.goalpanzi.mission_mate.core.main.component.MainNavigator
import com.goalpanzi.mission_mate.core.main.component.rememberMainNavigator
import com.goalpanzi.mission_mate.core.navigation.RouteModel

@Composable
internal fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
    startDestination: RouteModel
) {
    MainScreenContent(
        navigator = navigator,
        startDestination = startDestination
    )
}

@Composable
private fun MainScreenContent(
    navigator: MainNavigator,
    startDestination: RouteModel,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        content = { padding ->
            MainNavHost(
                navigator = navigator,
                startDestination = startDestination,
                padding = padding
            )
        }
    )
}