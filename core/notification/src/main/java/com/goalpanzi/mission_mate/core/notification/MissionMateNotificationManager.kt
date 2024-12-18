package com.goalpanzi.mission_mate.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import com.goalpanzi.mission_mate.core.notification.model.NotificationData
import javax.inject.Inject

class MissionMateNotificationManager @Inject constructor(
    private val context: Context
) {
    fun sendNotification(
        pendingIntent: PendingIntent,
        notificationData: NotificationData
    ) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = MISSION_MATE_CHANNEL_ID

        val channel = NotificationChannel(
            channelId,
            MISSION_MATE_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_round)
            .setContentTitle(notificationData.title)
            .setContentText(notificationData.description)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationManager.notify(MISSION_MATE_NOTIFICATION_DEFAULT_ID, notificationBuilder.build())
    }
}
