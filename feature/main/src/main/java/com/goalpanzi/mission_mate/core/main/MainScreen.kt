package com.goalpanzi.mission_mate.core.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.goalpanzi.mission_mate.core.main.component.MainNavHost
import com.goalpanzi.mission_mate.core.main.component.MainNavigator
import com.goalpanzi.mission_mate.core.main.component.rememberMainNavigator

@Composable
internal fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    MainScreenContent(
        navigator = navigator
    )
}

@Composable
private fun MainScreenContent(
    navigator: MainNavigator,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        content = { padding ->
            MainNavHost(
                navigator = navigator,
                padding = padding
            )
        }
    )
}