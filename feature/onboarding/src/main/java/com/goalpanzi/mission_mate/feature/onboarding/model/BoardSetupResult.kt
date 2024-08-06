package com.goalpanzi.mission_mate.feature.onboarding.model

import com.luckyoct.core.model.response.MissionDetailResponse

sealed class BoardSetupResult {
    data class Success(val data : MissionDetailResponse) : BoardSetupResult()
    data class Error(val message : String) : BoardSetupResult()
}