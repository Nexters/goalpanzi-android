package com.goalpanzi.mission_mate.core.data.auth.di

import com.goalpanzi.mission_mate.core.data.auth.repository.AuthRepositoryImpl
import com.goalpanzi.mission_mate.core.domain.auth.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AuthDataModule {

    @Binds
    abstract fun bindLoginRepository(impl: AuthRepositoryImpl): AuthRepository

}
