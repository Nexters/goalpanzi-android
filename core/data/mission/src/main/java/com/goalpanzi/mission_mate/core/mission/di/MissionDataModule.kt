package com.goalpanzi.mission_mate.core.mission.di

import com.goalpanzi.mission_mate.core.domain.mission.repository.MissionRepository
import com.goalpanzi.mission_mate.core.mission.repository.MissionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class MissionDataModule {

    @Binds
    abstract fun bindMissionRepository(impl: MissionRepositoryImpl): MissionRepository
}
