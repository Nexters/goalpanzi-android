package com.goalpanzi.mission_mate.feature.board.util.boardmanager

import com.goalpanzi.mission_mate.core.domain.model.mission.MissionVerification
import com.goalpanzi.mission_mate.feature.board.model.MissionState.Companion.isPassedEndTime
import com.goalpanzi.mission_mate.feature.board.model.MissionState.Companion.isTodayMissionDay
import com.goalpanzi.mission_mate.feature.board.model.MissionState.Companion.isVerifiedInMissionTime
import com.goalpanzi.mission_mate.feature.onboarding.model.VerificationTimeType
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime

class MissionStateUtilityMethodTests {

    // 인증 시간대가 오전이고 현재 날짜가 인증 종료일이고 현재 시간이 인증 시간대 이내라면 false를 반환한다
    @Test
    fun isPassedEndTime_MorningBeforeEnd_ReturnsFalse() {
        val todayLocalDateTime = LocalDateTime.of(2024, 8, 11, 10, 0, 0)
        val endDateTime = LocalDateTime.of(2024, 8, 11, 11, 59, 59)
        val verificationTimeType = VerificationTimeType.MORNING

        val result = isPassedEndTime(todayLocalDateTime, endDateTime, verificationTimeType)
        assertFalse(result)
    }

    // 인증 시간대가 오전이고 현재 날짜가 인증 종료일이고 현재 시간이 인증 시간대 이내가 아니라면 true를 반환한다
    @Test
    fun isPassedEndTime_MorningAfterEnd_ReturnsTrue() {
        val todayLocalDateTime = LocalDateTime.of(2024, 8, 11, 12, 0, 0)
        val endDateTime = LocalDateTime.of(2024, 8, 11, 11, 59, 59)
        val verificationTimeType = VerificationTimeType.MORNING

        val result = isPassedEndTime(todayLocalDateTime, endDateTime, verificationTimeType)
        assertTrue(result)
    }

    // 인증 시간대가 오후이고 현재 날짜가 인증 종료일이고 현재 시간이 인증 시간대 이내라면 false를 반환한다
    @Test
    fun isPassedEndTime_AfternoonBeforeEnd_ReturnsFalse() {
        val todayLocalDateTime = LocalDateTime.of(2024, 8, 11, 22, 0, 0)
        val endDateTime = LocalDateTime.of(2024, 8, 11, 23, 59, 59)
        val verificationTimeType = VerificationTimeType.AFTERNOON

        val result = isPassedEndTime(todayLocalDateTime, endDateTime, verificationTimeType)
        assertFalse(result)
    }

    // 인증 시간대가 오후이고 현재 날짜가 인증 종료일이고 현재 시간이 인증 시간대 이내가 아니라면 true를 반환한다
    @Test
    fun isPassedEndTime_AfternoonAfterEnd_ReturnsTrue() {
        val todayLocalDateTime = LocalDateTime.of(2024, 8, 12, 0, 0, 0)
        val endDateTime = LocalDateTime.of(2024, 8, 11, 23, 59, 59)
        val verificationTimeType = VerificationTimeType.AFTERNOON

        val result = isPassedEndTime(todayLocalDateTime, endDateTime, verificationTimeType)
        assertTrue(result)
    }

    // 인증 시간대가 종일이고 현재 날짜가 인증 종료일이고 현재 시간이 인증 시간대 이내라면 false를 반환한다
    @Test
    fun isPassedEndTime_EverydayBeforeEnd_ReturnsFalse() {
        val todayLocalDateTime = LocalDateTime.of(2024, 8, 11, 22, 0, 0)
        val endDateTime = LocalDateTime.of(2024, 8, 11, 23, 59, 59)
        val verificationTimeType = VerificationTimeType.EVERYDAY

        val result = isPassedEndTime(todayLocalDateTime, endDateTime, verificationTimeType)
        assertFalse(result)
    }

    // 인증 시간대가 종일이고 현재 날짜가 인증 종료일 이후라면 true를 반환한다
    @Test
    fun isPassedEndTime_EverydayAfterEnd_ReturnsTrue() {
        val todayLocalDateTime = LocalDateTime.of(2024, 8, 12, 0, 0, 0)
        val endDateTime = LocalDateTime.of(2024, 8, 11, 23, 59, 59)
        val verificationTimeType = VerificationTimeType.EVERYDAY

        val result = isPassedEndTime(todayLocalDateTime, endDateTime, verificationTimeType)
        assertTrue(result)
    }

    // 현재 날짜가 인증 종료일 이전이라면 false를 반환한다
    @Test
    fun isPassedEndTime_BeforeEndDate_ReturnsFalse() {
        val todayLocalDateTime = LocalDateTime.of(2024, 8, 10, 23, 59, 59)
        val endDateTime = LocalDateTime.of(2024, 8, 11, 23, 59, 59)
        val verificationTimeType = VerificationTimeType.EVERYDAY

        val result = isPassedEndTime(todayLocalDateTime, endDateTime, verificationTimeType)
        assertFalse(result)
    }

    // 미션 인증 요일 목록에 특정 요일이 포함되어 있으면 true를 반환한다
    @Test
    fun isTodayMissionDay_ContainsToday_ReturnsTrue() {
        val today = LocalDate.of(2024, 8, 14) // 수요일
        val missionDaysOfWeek = listOf(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)

        val result = isTodayMissionDay(today, missionDaysOfWeek)
        assertTrue(result)
    }

    // 미션 인증 요일 목록에 특정 요일이 포함되어 있지 않으면 false를 반환한다
    @Test
    fun isTodayMissionDay_DoesNotContainToday_ReturnsFalse() {
        val today = LocalDate.of(2024, 8, 14) // 수요일
        val missionDaysOfWeek = listOf(DayOfWeek.MONDAY, DayOfWeek.FRIDAY)

        val result = isTodayMissionDay(today, missionDaysOfWeek)
        assertFalse(result)
    }

    // 미션 인증 요일 목록이 빈 리스트이면 false를 반환한다
    @Test
    fun isTodayMissionDay_EmptyList_ReturnsFalse() {
        val today = LocalDate.of(2024, 8, 14) // 수요일
        val missionDaysOfWeek = emptyList<DayOfWeek>()

        val result = isTodayMissionDay(today, missionDaysOfWeek)
        assertFalse(result)
    }

    // 인증 멤버 목록이 비어있으면 false를 반환한다
    @Test
    fun isVerifiedInMissionTime_EmptyList_ReturnsFalse() {
        val memberList = emptyList<MissionVerification>()
        val result = isVerifiedInMissionTime(memberList)
        assertFalse(result)
    }

    // 인증 멤버 목록이 비어있지 않고 첫 항목의 이미지가 빈 문자열이면 false를 반환한다
    @Test
    fun isVerifiedInMissionTime_FirstImageEmpty_ReturnsFalse() {
        val memberList = listOf(
            MissionVerification(
                nickname = "",
                imageUrl = ""
            )
        )
        val result = isVerifiedInMissionTime(memberList)
        assertFalse(result)
    }

    // 인증 멤버 목록이 비어있지 않고 첫 항목의 이미지가 빈 문자열이 아니면 true를 반환한다
    @Test
    fun isVerifiedInMissionTime_FirstImageNotEmpty_ReturnsTrue() {
        val memberList = listOf(
            MissionVerification(
                nickname = "",
                imageUrl = "image_url"
            )
        )
        val result = isVerifiedInMissionTime(memberList)
        assertTrue(result)
    }
}
