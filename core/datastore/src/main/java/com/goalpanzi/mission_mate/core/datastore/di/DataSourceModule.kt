package com.goalpanzi.mission_mate.core.datastore.di

import com.goalpanzi.mission_mate.core.datastore.datasource.AuthDataSource
import com.goalpanzi.mission_mate.core.datastore.datasource.AuthDataSourceImpl
import com.goalpanzi.mission_mate.core.datastore.datasource.DefaultDataSource
import com.goalpanzi.mission_mate.core.datastore.datasource.DefaultDataSourceImpl
import com.goalpanzi.mission_mate.core.datastore.datasource.MissionDataSource
import com.goalpanzi.mission_mate.core.datastore.datasource.MissionDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindAuthDataSource(
        authDataSource: AuthDataSourceImpl
    ): AuthDataSource

    @Binds
    abstract fun bindDefaultDataSource(
        defaultDataSource: DefaultDataSourceImpl
    ): DefaultDataSource

    @Binds
    abstract fun bindMissionDataSource(
        missionDataSource: MissionDataSourceImpl
    ): MissionDataSource
}