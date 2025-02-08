package com.goalpanzi.mission_mate.feature.history.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

data class MissionHistory(
    val missionId: Long,
    val description: String,
    val myVerificationCount: Int,
    val totalVerificationCount: Int,
    val rank: Int,
    val imageUrls: List<String>,
    val memberCount: Int,
    private val missionStartDate: String,
    private val missionEndDate: String,
) {
    val missionFormattedStartDate: String by lazy {
        formatDate(missionStartDate)
    }

    val missionFormattedEndDate: String by lazy {
        formatDate(missionEndDate)
    }

    private fun formatDate(date: String): String {
        val formatter = DateTimeFormatter.ofPattern(FORMATTER_PATTERN_ISO8601_SSSZ)
        val localDate = LocalDate.parse(date, formatter)
        return DateTimeFormatter.ofPattern(FORMATTER_PATTERN, Locale.getDefault()).format(localDate)
    }

    companion object {
        private const val FORMATTER_PATTERN = "yyyy.MM.dd"
        private const val FORMATTER_PATTERN_ISO8601_SSSZ = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    }
}
