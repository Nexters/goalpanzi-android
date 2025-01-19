package com.goalpanzi.mission_mate.feature.history.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography

@Composable
fun HistoryRoute(
    modifier: Modifier = Modifier
) {
    HistoryScreen(
        modifier = modifier
    )
}

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "준비중입니다",
            style = MissionMateTypography.body_md_bold
        )
    }
}
