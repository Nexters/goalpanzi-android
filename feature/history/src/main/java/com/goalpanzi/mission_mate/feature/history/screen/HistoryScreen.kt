package com.goalpanzi.mission_mate.feature.history.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray3_FF727484
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionmateTheme
import com.goalpanzi.mission_mate.feature.history.R
import com.goalpanzi.mission_mate.feature.history.component.HistoryList
import com.goalpanzi.mission_mate.feature.history.model.MissionHistory

@Composable
fun HistoryRoute(
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    HistoryScreen(
        modifier = modifier,
        lazyListState = lazyListState
    )
}

@Composable
fun HistoryScreen(
    lazyListState : LazyListState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().statusBarsPadding().navigationBarsPadding()
    ) {
        HistoryTitle()
        HistoryListInfo(modifier = Modifier.padding(bottom = 12.dp))
        HistoryList(
            histories = emptyList(),
            lazyListState = lazyListState,
            onHistoryClick = { }
        )
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

@Composable
fun HistoryListInfo(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HistoryListTitle(titleRes = R.string.history_list_title_complete)

        HistoryListCount(count = 10)

        HistoryListSort(modifier = Modifier.weight(1f))
    }
}

@Composable
fun HistoryListTitle(
    @StringRes titleRes: Int,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = stringResource(titleRes),
        style = MissionMateTypography.title_lg_bold,
        color = ColorGray1_FF404249
    )
}

@Composable
fun HistoryListCount(
    count: Int,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = stringResource(R.string.history_list_count,count),
        style = MissionMateTypography.body_lg_regular,
        color = ColorGray3_FF727484
    )
}

@Composable
fun HistoryListSort(
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = stringResource(R.string.history_list_sort_latest_finished),
        style = MissionMateTypography.body_lg_regular,
        color = ColorGray3_FF727484,
        textAlign = TextAlign.End
    )
}

@Preview
@Composable
private fun HistoryScreenPreview() {
    MissionmateTheme {
        HistoryScreen(
            lazyListState = rememberLazyListState()
        )
    }
}

