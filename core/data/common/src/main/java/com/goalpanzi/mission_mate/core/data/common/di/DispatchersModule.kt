package com.goalpanzi.mission_mate.core.data.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatchers: MissionMateDispatcher)

enum class MissionMateDispatcher {
    IO
}

@Module
@InstallIn(SingletonComponent::class)
interface DispatchersModule {

    @Provides
    @Dispatcher(MissionMateDispatcher.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

}
