package com.goalpanzi.mission_mate.feature.login

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class LoginData {
    data class Success(val email : String) : LoginData()
    data class Failed(val exception: Exception) : LoginData()
}

class LoginManager (
    private val context : Context
) {
    suspend fun request() : LoginData = withContext(Dispatchers.IO) {
        val credentialManager = CredentialManager.create(context)
        val signInWithGoogleOption: GetSignInWithGoogleOption =
            GetSignInWithGoogleOption.Builder(BuildConfig.CREDENTIAL_WEB_CLIENT_ID)
                .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(signInWithGoogleOption)
            .build()

        return@withContext try {
            val result = credentialManager.getCredential(context, request)
            handleSignIn(result)
        } catch (e: Exception) {
            e.printStackTrace()
            LoginData.Failed(e)
        }
    }

    private fun handleSignIn(response: GetCredentialResponse) : LoginData {
        return when (val credential = response.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        LoginData.Success(googleIdTokenCredential.id)
                    } catch (e: GoogleIdTokenParsingException) {
                        LoginData.Failed(e)
                    }
                }else {
                    LoginData.Failed(RuntimeException("Credential Type not equals TYPE_GOOGLE_ID_TOKEN_CREDENTIAL"))
                }
            }

            else -> {
                LoginData.Failed(RuntimeException("Credential is not CustomCredential"))
            }
        }
    }
}
