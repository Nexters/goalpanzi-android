package com.goalpanzi.mission_mate.feature.onboarding.model

import androidx.annotation.StringRes
import com.goalpanzi.mission_mate.feature.onboarding.R

enum class VerificationTimeType(
    @StringRes val titleId : Int
) {
    Am(R.string.onboarding_board_setup_verification_time_input_content_am),
    Pm(R.string.onboarding_board_setup_verification_time_input_content_pm),
    All(R.string.onboarding_board_setup_verification_time_input_content_all)
}