package com.goalpanzi.mission_mate.core.data.repository

import com.goalpanzi.mission_mate.core.data.mapper.toDto
import com.goalpanzi.mission_mate.core.data.mapper.toModel
import com.goalpanzi.mission_mate.core.datastore.datasource.AuthDataSource
import com.goalpanzi.mission_mate.core.datastore.datasource.DefaultDataSource
import com.goalpanzi.mission_mate.core.domain.model.UserProfile
import com.goalpanzi.mission_mate.core.domain.repository.DefaultRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultRepositoryImpl @Inject constructor(
    private val defaultDataSource: DefaultDataSource
) : DefaultRepository {
    override fun clearUserData(): Flow<Unit> = defaultDataSource.clearUserData()

    override fun setUserProfile(data: UserProfile): Flow<Unit> = defaultDataSource.setUserProfile(data.toDto())

    override fun getUserProfile(): Flow<UserProfile?> = defaultDataSource.getUserProfile().map { it?.toModel() }

    override fun getViewedTooltip(): Flow<Boolean> = defaultDataSource.getViewedTooltip()

    override fun setViewedTooltip(): Flow<Unit> = defaultDataSource.setViewedTooltip()

    override fun setMemberId(data: Long): Flow<Unit> = defaultDataSource.setMemberId(data)

    override fun getMemberId(): Flow<Long?> = defaultDataSource.getMemberId()

}