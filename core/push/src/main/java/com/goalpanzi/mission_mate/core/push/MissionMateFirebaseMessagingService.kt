package com.goalpanzi.mission_mate.core.push

import android.app.PendingIntent
import android.content.Intent
import com.goalpanzi.mission_mate.core.notification.MissionMateNotificationManager
import com.goalpanzi.mission_mate.core.notification.model.NotificationData
import com.goalpanzi.mission_mate.feature.main.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MissionMateFirebaseMessagingService : FirebaseMessagingService() {
    @Inject lateinit var missionMateNotificationManager: MissionMateNotificationManager

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.data.let { data ->
            val title = data[TITLE_KEY] ?: MissionMateNotificationManager.DEFAULT_TITLE
            val description = data[DESCRIPTION_KEY] ?: MissionMateNotificationManager.DEFAULT_DESCRIPTION

            sendNotification(title, description)
        }
    }

    private fun sendNotification(title: String, description: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            MainActivity.NOTIFICATION_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        missionMateNotificationManager.sendNotification(
            pendingIntent = pendingIntent,
            notificationData = NotificationData(
                title = title,
                description = description
            )
        )
    }

    companion object {
        private const val TITLE_KEY = "title"
        private const val DESCRIPTION_KEY = "body"
    }
}

