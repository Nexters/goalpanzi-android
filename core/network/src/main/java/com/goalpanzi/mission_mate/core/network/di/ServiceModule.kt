package com.goalpanzi.mission_mate.core.network.di

import com.goalpanzi.mission_mate.core.network.service.LoginService
import com.goalpanzi.mission_mate.core.network.service.OnboardingService
import com.goalpanzi.mission_mate.core.network.service.ProfileService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileService(retrofit: Retrofit): ProfileService {
        return retrofit.create(ProfileService::class.java)
    }

    @Provides
    @Singleton
    fun provideOnboardingService(retrofit: Retrofit): OnboardingService {
        return retrofit.create(OnboardingService::class.java)
    }
}