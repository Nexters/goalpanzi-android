package com.goalpanzi.mission_mate.core.domain.repository

import com.goalpanzi.mission_mate.core.network.ResultHandler
import com.luckyoct.core.model.base.NetworkResult

interface ProfileRepository: ResultHandler {
    suspend fun saveProfile(nickname: String, index: Int): NetworkResult<Unit>
}