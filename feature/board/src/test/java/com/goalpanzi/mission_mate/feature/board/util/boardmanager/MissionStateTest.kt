package com.goalpanzi.mission_mate.feature.board.util.boardmanager

import com.goalpanzi.mission_mate.feature.board.model.MissionState
import com.goalpanzi.mission_mate.feature.board.model.MissionState.Companion.getMissionState
import com.goalpanzi.mission_mate.feature.onboarding.model.VerificationTimeType
import com.luckyoct.core.model.response.MissionVerificationResponse
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.assertNotEquals

class MissionStateTest {

    @Test
    fun 현재_날짜가_인증_시작일이고_멤버_수가_1이하_일때_DELETABLE를_반환한다() {
        val todayLocalDateTime = LocalDateTime.of(2024, 8, 14, 10, 0, 0) // 오전 10시
        val startDate = LocalDate.of(2024, 8, 14) // 시작 날짜가 오늘
        val endDateTime = LocalDateTime.of(2024, 8, 15, 23, 59, 59)
        val memberList = emptyList<MissionVerificationResponse>()
        val verificationTimeType = VerificationTimeType.MORNING
        val daysOfWeek = listOf(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)

        val result = getMissionState(
            todayLocalDateTime,
            startDate,
            endDateTime,
            memberList,
            verificationTimeType,
            daysOfWeek
        )
        assertEquals(MissionState.DELETABLE, result)
    }

    @Test
    fun 현재_날짜가_인증_시작일_이후이고_멤버_수가_1이하_일때_DELETABLE를_반환한다() {
        val todayLocalDateTime = LocalDateTime.of(2024, 8, 16, 10, 0, 0) // 오전 10시
        val startDate = LocalDate.of(2024, 8, 14)
        val endDateTime = LocalDateTime.of(2024, 8, 15, 23, 59, 59)
        val memberList = emptyList<MissionVerificationResponse>()
        val verificationTimeType = VerificationTimeType.MORNING
        val daysOfWeek = listOf(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)

        val result = getMissionState(
            todayLocalDateTime,
            startDate,
            endDateTime,
            memberList,
            verificationTimeType,
            daysOfWeek
        )
        assertEquals(MissionState.DELETABLE, result)
    }

    @Test
    fun 현재_날짜가_인증_시작일_이전이고_멤버_수가_1이하_일때_PRE_START_SOLO를_반환한다() {
        val todayLocalDateTime = LocalDateTime.of(2024, 8, 14, 10, 0, 0)
        val startDate = LocalDate.of(2024, 8, 15)
        val endDateTime = LocalDateTime.of(2024, 8, 20, 23, 59, 59)
        val memberList = listOf(MissionVerificationResponse(nickname = "user", imageUrl = "image_url"))
        val verificationTimeType = VerificationTimeType.MORNING
        val daysOfWeek = listOf(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)

        val result = getMissionState(todayLocalDateTime, startDate, endDateTime, memberList, verificationTimeType, daysOfWeek)
        assertEquals(MissionState.PRE_START_SOLO, result)
    }

    @Test
    fun 현재_날짜가_인증_시작일_이전이고_멤버_수가_2이상_일때_PRE_START_MULTI를_반환한다() {
        val todayLocalDateTime = LocalDateTime.of(2024, 8, 14, 10, 0, 0)
        val startDate = LocalDate.of(2024, 8, 15)
        val endDateTime = LocalDateTime.of(2024, 8, 20, 23, 59, 59)
        val memberList = listOf(
            MissionVerificationResponse(nickname = "user1", imageUrl = "image_url"),
            MissionVerificationResponse(nickname = "user2", imageUrl = "image_url")
        )
        val verificationTimeType = VerificationTimeType.MORNING
        val daysOfWeek = listOf(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)

        val result = getMissionState(todayLocalDateTime, startDate, endDateTime, memberList, verificationTimeType, daysOfWeek)
        assertEquals(MissionState.PRE_START_MULTI, result)
    }

    @Test
    fun 현재_날짜가_인증_마감일_이후일_때_POST_END를_반환한다() {
        val todayLocalDateTime = LocalDateTime.of(2024, 8, 16, 0, 0, 0)
        val startDate = LocalDate.of(2024, 8, 13)
        val endDateTime = LocalDateTime.of(2024, 8, 15, 23, 59, 59)
        val memberList = listOf(MissionVerificationResponse(nickname = "user", imageUrl = "image_url"))
        val verificationTimeType = VerificationTimeType.MORNING
        val daysOfWeek = listOf(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)

        val result = getMissionState(todayLocalDateTime, startDate, endDateTime, memberList, verificationTimeType, daysOfWeek)
        assertEquals(MissionState.POST_END, result)
    }

    @Test
    fun 인증_시간대가_오전이고_현재_날짜가_인증_마감일이고_현재_시간이_인증_마감_시간_이후일_때_POST_END를_반환한다() {
        val todayLocalDateTime = LocalDateTime.of(2024, 8, 15, 12, 0, 0)
        val startDate = LocalDate.of(2024, 8, 13)
        val endDateTime = LocalDateTime.of(2024, 8, 15, 0, 0, 0)
        val memberList = listOf(MissionVerificationResponse(nickname = "user", imageUrl = "image_url"))
        val verificationTimeType = VerificationTimeType.MORNING
        val daysOfWeek = listOf(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)

        val result = getMissionState(todayLocalDateTime, startDate, endDateTime, memberList, verificationTimeType, daysOfWeek)
        assertEquals(MissionState.POST_END, result)
    }

    @Test
    fun 인증_시간대가_오전이_아니고_현재_날짜가_인증_마감일일_때_POST_END를_반환하지_않는다() {
        val todayLocalDateTime = LocalDateTime.of(2024, 8, 15, 12, 0, 0)
        val startDate = LocalDate.of(2024, 8, 13)
        val endDateTime = LocalDateTime.of(2024, 8, 15, 0, 0, 0)
        val memberList = listOf(MissionVerificationResponse(nickname = "user", imageUrl = "image_url"))
        val verificationTimeType = VerificationTimeType.AFTERNOON
        val daysOfWeek = listOf(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)

        val result = getMissionState(todayLocalDateTime, startDate, endDateTime, memberList, verificationTimeType, daysOfWeek)
        assertNotEquals(MissionState.POST_END, result)
    }
}