package com.goalpanzi.mission_mate.core.push.di

import com.goalpanzi.mission_mate.core.data.user.FcmTokenManager
import com.goalpanzi.mission_mate.core.push.FcmTokenManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FcmModule {
    @Binds
    abstract fun bindFcmTokenManager(
        impl: FcmTokenManagerImpl
    ): FcmTokenManager
}
