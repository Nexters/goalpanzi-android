package com.goalpanzi.mission_mate.core.data.user.di

import com.goalpanzi.mission_mate.core.data.user.repository.UserRepositoryImpl
import com.goalpanzi.mission_mate.core.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UserDataModule {
    @Binds
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

}
