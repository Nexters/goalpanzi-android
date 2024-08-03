package com.goalpanzi.mission_mate.feature.onboarding.util

import com.goalpanzi.mission_mate.core.designsystem.R
import java.time.DayOfWeek

fun DayOfWeek.getStringId() : Int {
    return when(this){
        DayOfWeek.MONDAY -> R.string.monday_short
        DayOfWeek.TUESDAY -> R.string.tuesday_short
        DayOfWeek.WEDNESDAY -> R.string.wednesday_short
        DayOfWeek.THURSDAY -> R.string.thursday_short
        DayOfWeek.FRIDAY -> R.string.friday_short
        DayOfWeek.SATURDAY -> R.string.saturday_short
        DayOfWeek.SUNDAY -> R.string.sunday_short
    }
}