package com.goalpanzi.mission_mate.core.data.user

import kotlinx.coroutines.flow.Flow

interface FcmTokenManager {
    fun getFcmToken() : Flow<String>
}
