package com.goalpanzi.mission_mate.core.data.onboarding.di

import com.goalpanzi.mission_mate.core.data.onboarding.repository.OnboardingRepositoryImpl
import com.goalpanzi.mission_mate.core.domain.repository.OnboardingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class OnboardingDataModule {

    @Binds
    abstract fun bindOnboardingRepository(impl: OnboardingRepositoryImpl): OnboardingRepository

}
