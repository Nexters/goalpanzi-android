package com.goalpanzi.mission_mate.feature.board.util.boardmanager

import com.goalpanzi.mission_mate.core.domain.model.mission.MissionVerification
import com.goalpanzi.mission_mate.feature.board.model.MissionState
import com.goalpanzi.mission_mate.feature.board.model.MissionState.Companion.getMissionStateAsInProgress
import com.goalpanzi.mission_mate.feature.onboarding.model.VerificationTimeType
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime

class MissionStateAsInProgressTest {

    @Test
    fun 오늘이_인증_요일이고_현재_시간이_인증_시간대_이전일_때_IN_PROGRESS_MISSION_DAY_NON_MISSION_TIME를_반환한다() {
        val todayLocalDate = LocalDate.of(2024, 8, 14) // 수요일
        val todayLocalDateTime = LocalDateTime.of(2024, 8, 14, 11, 59, 59)
        val daysOfWeek = listOf(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)
        val verificationTimeType = VerificationTimeType.AFTERNOON
        val memberList = listOf(
            MissionVerification(
                nickname = "",
                imageUrl = "image"
            )
        )

        val result = getMissionStateAsInProgress(todayLocalDate, todayLocalDateTime, daysOfWeek, verificationTimeType, memberList)

        assertEquals(MissionState.IN_PROGRESS_MISSION_DAY_NON_MISSION_TIME, result)
    }

    @Test
    fun 오늘이_인증_요일이고_현재_시간이_인증_시간대_이후일_때_IN_PROGRESS_MISSION_DAY_CLOSED를_반환한다() {
        val todayLocalDate = LocalDate.of(2024, 8, 14) // 수요일
        val todayLocalDateTime = LocalDateTime.of(2024, 8, 14, 12, 0, 0)
        val daysOfWeek = listOf(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)
        val verificationTimeType = VerificationTimeType.MORNING
        val memberList = listOf(
            MissionVerification(
                nickname = "",
                imageUrl = ""
            )
        )

        val result = getMissionStateAsInProgress(todayLocalDate, todayLocalDateTime, daysOfWeek, verificationTimeType, memberList)

        assertEquals(MissionState.IN_PROGRESS_MISSION_DAY_CLOSED, result)
    }

    @Test
    fun 오늘이_인증_요일이고_현재_시간이_인증_시간대일_때_인증한_이미지_데이터가_없으면_IN_PROGRESS_MISSION_DAY_BEFORE_CONFIRM를_반환한다() {
        val todayLocalDate = LocalDate.of(2024, 8, 14) // 수요일
        val todayLocalDateTime = LocalDateTime.of(2024, 8, 14, 10, 0, 0)
        val daysOfWeek = listOf(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)
        val verificationTimeType = VerificationTimeType.MORNING
        val memberList = listOf(
            MissionVerification(
                nickname = "",
                imageUrl = ""
            )
        )

        val result = getMissionStateAsInProgress(todayLocalDate, todayLocalDateTime, daysOfWeek, verificationTimeType, memberList)

        assertEquals(MissionState.IN_PROGRESS_MISSION_DAY_BEFORE_CONFIRM, result)
    }

    @Test
    fun 오늘이_인증_요일이고_현재_시간이_인증_시간대일_때_인증한_이미지_데이터가_있으면_IN_PROGRESS_MISSION_DAY_AFTER_CONFIRM를_반환한다() {
        val todayLocalDate = LocalDate.of(2024, 8, 14) // 수요일
        val todayLocalDateTime = LocalDateTime.of(2024, 8, 14, 10, 0, 0)
        val daysOfWeek = listOf(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)
        val verificationTimeType = VerificationTimeType.MORNING
        val memberList = listOf(
            MissionVerification(
                nickname = "user",
                imageUrl = "image_url"
            )
        )

        val result = getMissionStateAsInProgress(todayLocalDate, todayLocalDateTime, daysOfWeek, verificationTimeType, memberList)

        assertEquals(MissionState.IN_PROGRESS_MISSION_DAY_AFTER_CONFIRM, result)
    }

    @Test
    fun 오늘이_인증_요일이_아니면_IN_PROGRESS_NON_MISSION_DAY를_반환한다() {
        val todayLocalDate = LocalDate.of(2024, 8, 13) // 화요일
        val todayLocalDateTime = LocalDateTime.of(2024, 8, 13, 10, 0, 0)
        val daysOfWeek = listOf(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)
        val verificationTimeType = VerificationTimeType.MORNING
        val memberList = emptyList<MissionVerification>()

        val result = getMissionStateAsInProgress(todayLocalDate, todayLocalDateTime, daysOfWeek, verificationTimeType, memberList)

        assertEquals(MissionState.IN_PROGRESS_NON_MISSION_DAY, result)
    }
}