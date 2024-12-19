package com.goalpanzi.mission_mate.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goalpanzi.mission_mate.core.domain.user.usecase.GetFcmTokenUseCase
import com.goalpanzi.mission_mate.core.domain.user.usecase.UpdateFcmTokenUseCase
import com.goalpanzi.mission_mate.core.navigation.NavigationEventHandler
import com.goalpanzi.mission_mate.core.navigation.di.AuthNavigation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @AuthNavigation navigationEventHandler : NavigationEventHandler,
    private val updateFcmTokenUseCase: UpdateFcmTokenUseCase,
    private val getFcmTokenUseCase: GetFcmTokenUseCase,
) : ViewModel() {
    val navigationEvent = navigationEventHandler.routeToNavigate

    init {
        viewModelScope.launch {
            getFcmTokenUseCase().collect { token ->
                updateFcmTokenUseCase(token)
            }
        }
    }
}
