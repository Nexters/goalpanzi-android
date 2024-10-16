package com.goalpanzi.mission_mate.core.main

import androidx.lifecycle.ViewModel
import com.goalpanzi.mission_mate.core.navigation.NavigationEventHandler
import com.goalpanzi.mission_mate.core.navigation.di.AuthNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @AuthNavigation navigationEventHandler : NavigationEventHandler
) : ViewModel() {
    @OptIn(FlowPreview::class)
    val navigationEvent = navigationEventHandler.routeToNavigate.debounce(1_000)
}
