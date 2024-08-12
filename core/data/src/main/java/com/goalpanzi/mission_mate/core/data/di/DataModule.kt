package com.goalpanzi.mission_mate.core.data.di

import com.goalpanzi.mission_mate.core.data.repository.AuthRepositoryImpl
import com.goalpanzi.mission_mate.core.data.repository.OnboardingRepositoryImpl
import com.goalpanzi.mission_mate.core.data.repository.ProfileRepositoryImpl
import com.goalpanzi.mission_mate.core.domain.repository.AuthRepository
import com.goalpanzi.mission_mate.core.domain.repository.OnboardingRepository
import com.goalpanzi.mission_mate.core.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {

    @Binds
    abstract fun bindLoginRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    abstract fun bindOnboardingRepository(impl: OnboardingRepositoryImpl): OnboardingRepository
}