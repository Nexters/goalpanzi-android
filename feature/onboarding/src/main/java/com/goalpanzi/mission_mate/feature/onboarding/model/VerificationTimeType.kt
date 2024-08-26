package com.goalpanzi.mission_mate.feature.onboarding.model

import androidx.annotation.StringRes
import com.goalpanzi.mission_mate.feature.onboarding.R
import java.time.LocalDateTime

enum class VerificationTimeType(
    @StringRes val titleId : Int
) {
    MORNING(R.string.onboarding_board_setup_verification_time_input_content_am),
    AFTERNOON(R.string.onboarding_board_setup_verification_time_input_content_pm),
    EVERYDAY(R.string.onboarding_board_setup_verification_time_input_content_all);

    fun getVerificationEndTime(
        targetTime : LocalDateTime
    ) : LocalDateTime {
        return when (this) {
            MORNING -> targetTime.withHour(11)
            else -> targetTime.withHour(23)
        }.withMinute(59).withSecond(59).withNano(999_999_999)
    }
}