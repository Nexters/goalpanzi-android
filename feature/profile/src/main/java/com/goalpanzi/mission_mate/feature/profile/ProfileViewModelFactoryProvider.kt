package com.goalpanzi.mission_mate.feature.profile

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ProfileViewModelFactoryProvider {

    fun profileViewModelFactory(): ProfileViewModel.Factory
}