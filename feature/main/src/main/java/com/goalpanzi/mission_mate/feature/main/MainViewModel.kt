package com.goalpanzi.mission_mate.feature.main

import androidx.lifecycle.ViewModel
import com.goalpanzi.mission_mate.core.navigation.NavigationEventHandler
import com.goalpanzi.mission_mate.core.navigation.di.AuthNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @AuthNavigation navigationEventHandler : NavigationEventHandler,
) : ViewModel() {
    val navigationEvent = navigationEventHandler.routeToNavigate
}
