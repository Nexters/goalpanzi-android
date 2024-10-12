package com.goalpanzi.mission_mate.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goalpanzi.mission_mate.core.domain.auth.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<LoginEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    suspend fun login(loginData: LoginData){
        when(loginData){
            is LoginData.Success -> {
                try {
                    val result = loginUseCase.requestGoogleLogin(email = loginData.email)
                    _eventFlow.emit(
                        result?.let {
                            LoginEvent.Success(it.isProfileSet)
                        } ?: run {
                            LoginEvent.Error
                        }
                    )
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
            is LoginData.Failed -> {
                loginData.exception.printStackTrace()
            }
        }
    }
}
