package com.goalpanzi.mission_mate.feature.onboarding.model

import com.goalpanzi.mission_mate.core.domain.model.mission.MissionDetail

sealed class BoardSetupResult {
    data class Success(val data :MissionDetail) : BoardSetupResult()
    data class Error(val message : String) : BoardSetupResult()
}