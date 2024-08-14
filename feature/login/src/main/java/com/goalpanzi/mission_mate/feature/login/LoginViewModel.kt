package com.goalpanzi.mission_mate.feature.login

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goalpanzi.mission_mate.core.domain.usecase.LoginUseCase
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
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

    fun request(context: Context) {
        viewModelScope.launch {
            val credentialManager = CredentialManager.create(context)
            val signInWithGoogleOption: GetSignInWithGoogleOption =
                GetSignInWithGoogleOption.Builder(BuildConfig.CREDENTIAL_WEB_CLIENT_ID)
                    .build()

            val request: GetCredentialRequest = GetCredentialRequest.Builder()
                .addCredentialOption(signInWithGoogleOption)
                .build()

            try {
                val result = credentialManager.getCredential(context, request)
                handleSignIn(result)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun handleSignIn(response: GetCredentialResponse) {
        when (val credential = response.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        val result = loginUseCase.requestGoogleLogin(email = googleIdTokenCredential.id)
                        _eventFlow.emit(
                            result?.let {
                                LoginEvent.Success(it.isProfileSet)
                            } ?: run {
                                LoginEvent.Error
                            }
                        )
                    } catch (e: GoogleIdTokenParsingException) {
                        e.printStackTrace()
                    }
                }
            }

            else -> {
                // TODO : error event
            }
        }
    }
}