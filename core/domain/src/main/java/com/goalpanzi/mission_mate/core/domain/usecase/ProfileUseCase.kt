package com.goalpanzi.mission_mate.core.domain.usecase

import com.goalpanzi.mission_mate.core.datastore.datasource.DefaultDataSource
import com.goalpanzi.mission_mate.core.domain.repository.ProfileRepository
import com.goalpanzi.core.model.CharacterType
import com.goalpanzi.core.model.UserProfile
import com.goalpanzi.core.model.base.NetworkResult
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val defaultDataSource: DefaultDataSource
) {
    suspend fun saveProfile(nickname: String, type: CharacterType) = profileRepository.saveProfile(nickname, type).also {
        if (it is NetworkResult.Success) {
            defaultDataSource.setUserProfile(UserProfile(nickname, type)).first()
        }
    }
    suspend fun getProfile(): UserProfile? = defaultDataSource.getUserProfile().first()
}