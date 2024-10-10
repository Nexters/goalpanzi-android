package com.goalpanzi.mission_mate.core.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.goalpanzi.mission_mate.core.main.component.MainNavHost
import com.goalpanzi.mission_mate.core.main.component.MainNavigator
import com.goalpanzi.mission_mate.core.main.component.rememberMainNavigator

@Composable
internal fun MainScreen(
    startDestination: String,
    navigator: MainNavigator = rememberMainNavigator(),
    viewModel: MainViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect {
            navigator.navController.navigate(it)
        }
    }

    MainScreenContent(
        navigator = navigator,
        startDestination = startDestination
    )
}

@Composable
private fun MainScreenContent(
    navigator: MainNavigator,
    startDestination: String,
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
