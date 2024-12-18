package com.goalpanzi.mission_mate.core.notification.di

import android.content.Context
import com.goalpanzi.mission_mate.core.notification.MissionMateNotificationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NotificationModule {
    @Singleton
    @Provides
    fun provideMissionMateNotificationManager(
        @ApplicationContext context: Context
    ): MissionMateNotificationManager {
        return MissionMateNotificationManager(context)
    }
}
