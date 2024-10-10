package com.goalpanzi.mission_mate.core.navigation.di

import com.goalpanzi.mission_mate.core.navigation.AuthNavigationEventHandler
import com.goalpanzi.mission_mate.core.navigation.NavigationEventHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class AuthNavigation

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {

    @AuthNavigation
    @Binds
    @Singleton
    abstract fun bindAuthNavigationEventHandler(
        impl : AuthNavigationEventHandler
    ) : NavigationEventHandler
}
