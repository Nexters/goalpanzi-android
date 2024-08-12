package com.luckyoct.feature.setting.screen

import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.component.MissionMateTopAppBar
import com.goalpanzi.mission_mate.core.designsystem.theme.component.NavigationType

@Composable
fun WebViewScreen(
    onBackClick: () -> Unit,
    url: String
) {
    Column(
        modifier = Modifier
            .background(ColorWhite_FFFFFFFF)
            .statusBarsPadding()
    ) {
        MissionMateTopAppBar(
            navigationType = NavigationType.BACK,
            containerColor = ColorWhite_FFFFFFFF,
            onNavigationClick = { onBackClick() }
        )

        AndroidView(
            factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    webChromeClient = CustomWebChromeClient()
                }
            }, update = {
                it.loadUrl(url)
            }
        )
    }

}

class CustomWebChromeClient : WebChromeClient() {
    override fun onCloseWindow(window: WebView?) {}
}