package com.goalpanzi.mission_mate.core.data.auth.di

import com.goalpanzi.mission_mate.core.data.auth.AuthTokenExpirationHandler
import com.goalpanzi.mission_mate.core.data.auth.AuthTokenProvider
import com.goalpanzi.mission_mate.core.data.auth.repository.AuthRepositoryImpl
import com.goalpanzi.mission_mate.core.domain.auth.repository.AuthRepository
import com.goalpanzi.mission_mate.core.network.TokenExpirationHandler
import com.goalpanzi.mission_mate.core.network.TokenProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class AuthDataModule {

    @Binds
    abstract fun bindLoginRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindTokenProvider(impl : AuthTokenProvider) : TokenProvider

    @Binds
    abstract fun bindTokenExpirationHandler(impl : AuthTokenExpirationHandler) : TokenExpirationHandler
}
