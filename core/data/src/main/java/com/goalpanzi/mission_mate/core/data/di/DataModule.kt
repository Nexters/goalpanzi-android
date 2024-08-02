package com.goalpanzi.mission_mate.core.data.di

import com.goalpanzi.mission_mate.core.data.repository.LoginRepositoryImpl
import com.goalpanzi.mission_mate.core.data.repository.ProfileRepositoryImpl
import com.goalpanzi.mission_mate.core.domain.repository.LoginRepository
import com.goalpanzi.mission_mate.core.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {

    @Binds
    abstract fun bindLoginRepository(impl: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository
}