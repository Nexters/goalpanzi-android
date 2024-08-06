package com.goalpanzi.mission_mate.feature.onboarding.model

import androidx.annotation.StringRes
import com.goalpanzi.mission_mate.feature.onboarding.R

enum class VerificationTimeType(
    @StringRes val titleId : Int
) {
    MORNING(R.string.onboarding_board_setup_verification_time_input_content_am),
    AFTERNOON(R.string.onboarding_board_setup_verification_time_input_content_pm),
    EVERYDAY(R.string.onboarding_board_setup_verification_time_input_content_all)
}