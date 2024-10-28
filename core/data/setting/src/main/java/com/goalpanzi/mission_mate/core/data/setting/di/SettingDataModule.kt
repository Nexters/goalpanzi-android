package com.goalpanzi.mission_mate.core.data.setting.di

import com.goalpanzi.mission_mate.core.data.setting.repository.SettingRepositoryImpl
import com.goalpanzi.mission_mate.core.domain.setting.repository.SettingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SettingDataModule {

    @Binds
    abstract fun bindDefaultRepository(impl: SettingRepositoryImpl): SettingRepository

}
