package com.goalpanzi.mission_mate.core.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionmateTheme
import com.goalpanzi.mission_mate.core.main.component.MainNavigator
import com.goalpanzi.mission_mate.core.main.component.rememberMainNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        setContent {
            val navigator: MainNavigator = rememberMainNavigator()
            MissionmateTheme {
                MainScreen(
                    navigator = navigator,
                )
            }
        }
    }
}
