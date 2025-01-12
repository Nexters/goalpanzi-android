package com.goalpanzi.mission_mate.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goalpanzi.mission_mate.core.navigation.NavigationEventHandler
import com.goalpanzi.mission_mate.core.navigation.RouteModel
import com.goalpanzi.mission_mate.core.navigation.di.AuthNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @AuthNavigation navigationEventHandler : NavigationEventHandler,
) : ViewModel() {
    val navigationEvent = navigationEventHandler.routeToNavigate
}
