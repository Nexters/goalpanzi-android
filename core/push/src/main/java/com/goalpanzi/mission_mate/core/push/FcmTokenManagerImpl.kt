    package com.goalpanzi.mission_mate.core.push

import com.goalpanzi.mission_mate.core.data.user.FcmTokenManager
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FcmTokenManagerImpl @Inject constructor() : FcmTokenManager {
    override fun getFcmToken(): Flow<String> = callbackFlow {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@addOnCompleteListener
            }
            val token = task.result
            trySend(token)
        }
        awaitClose { /* No cleanup necessary */ }
    }
}
