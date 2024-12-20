package com.goalpanzi.mission_mate.core.data.common.di

import android.content.Context
import com.goalpanzi.mission_mate.core.data.common.DeviceInfoProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DeviceInfoProviderModule {
    @Singleton
    @Provides
    fun provideADeviceInfoProvider(
        @ApplicationContext context: Context
    ): DeviceInfoProvider {
        return DeviceInfoProvider(context)
    }
}
