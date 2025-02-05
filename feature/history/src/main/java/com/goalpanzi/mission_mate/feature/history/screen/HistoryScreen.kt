package com.goalpanzi.mission_mate.feature.history.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionmateTheme
import com.goalpanzi.mission_mate.feature.history.R

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
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        HistoryTitle()
    }
}

@Composable
fun HistoryTitle(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = R.string.history_title),
        modifier = modifier
            .wrapContentWidth()
            .padding(start = 24.dp, top = 20.dp, bottom = 16.dp, end = 24.dp),
        style = MissionMateTypography.heading_sm_bold,
        color = ColorGray1_FF404249
    )
}

@Preview
@Composable
private fun HistoryScreenPreview() {
    MissionmateTheme {
        HistoryScreen()
    }
}
