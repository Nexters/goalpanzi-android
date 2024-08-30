package com.goalpanzi.mission_mate.feature.setting.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goalpanzi.mission_mate.feature.setting.Event
import com.goalpanzi.mission_mate.core.domain.usecase.AccountDeleteUseCase
import com.goalpanzi.mission_mate.core.domain.usecase.login.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val accountDeleteUseCase: AccountDeleteUseCase
) : ViewModel() {

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.invoke().collectLatest {
                _event.emit(Event.GoToLogin)
            }
        }
    }

    fun deleteAccount() {
        viewModelScope.launch {
            accountDeleteUseCase.invoke().collectLatest {
                _event.emit(Event.GoToLogin)
            }
        }
    }
}