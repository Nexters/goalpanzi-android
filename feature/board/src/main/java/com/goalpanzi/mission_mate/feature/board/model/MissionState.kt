package com.goalpanzi.mission_mate.feature.board.model

import com.goalpanzi.mission_mate.feature.board.model.uimodel.MissionBoardUiModel
import com.goalpanzi.mission_mate.feature.board.model.uimodel.MissionUiModel
import com.goalpanzi.mission_mate.feature.board.model.uimodel.MissionVerificationUiModel
import com.goalpanzi.mission_mate.feature.onboarding.model.VerificationTimeType
import com.luckyoct.core.model.response.MissionVerificationResponse
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime

enum class MissionState {
    LOADING,

    DELETABLE,       // 삭제_가능

    PRE_START_SOLO,            // 시작_전_혼자
    PRE_START_MULTI,           // 시작_전_2명_이상

    IN_PROGRESS_MISSION_DAY_BEFORE_CONFIRM,  // 진행_중_미션일_인증_전
    IN_PROGRESS_MISSION_DAY_AFTER_CONFIRM,   // 진행_중_미션일_인증_후
    IN_PROGRESS_MISSION_DAY_CLOSED,          // 진행_중_미션일_인증_마감
    IN_PROGRESS_NON_MISSION_DAY,             // 진행_중_미션일_아님
    IN_PROGRESS_MISSION_DAY_NON_MISSION_TIME, // 진행_중_미션일O_미션시간X

    POST_END;                      // 종료_후

    fun isVisiblePiece() : Boolean {
        return this in setOf(
            IN_PROGRESS_MISSION_DAY_BEFORE_CONFIRM,
            IN_PROGRESS_MISSION_DAY_AFTER_CONFIRM,
            IN_PROGRESS_MISSION_DAY_CLOSED,
            IN_PROGRESS_NON_MISSION_DAY,
            IN_PROGRESS_MISSION_DAY_NON_MISSION_TIME,
            POST_END
        )
    }

    fun enabledVerification() : Boolean {
        return this == IN_PROGRESS_MISSION_DAY_BEFORE_CONFIRM
    }

    fun isRankBoardTitle() : Boolean {
        return this in setOf(
            IN_PROGRESS_MISSION_DAY_BEFORE_CONFIRM,
            IN_PROGRESS_MISSION_DAY_AFTER_CONFIRM
        )
    }

    fun isEncourageBoardTitle() : Boolean {
        return this in setOf(
            IN_PROGRESS_MISSION_DAY_CLOSED,
            IN_PROGRESS_NON_MISSION_DAY,
            IN_PROGRESS_MISSION_DAY_NON_MISSION_TIME
        )
    }

    companion object {
        fun getMissionState(
            missionBoardUiModel: MissionBoardUiModel,
            missionUiModel: MissionUiModel,
            missionVerificationUiModel: MissionVerificationUiModel
        ): MissionState {
            if (missionBoardUiModel !is MissionBoardUiModel.Success) return LOADING
            if (missionUiModel !is MissionUiModel.Success) return LOADING
            if (missionVerificationUiModel !is MissionVerificationUiModel.Success) return LOADING

            val todayLocalDateTime = LocalDateTime.now()

            return getMissionState(
                todayLocalDateTime = todayLocalDateTime,
                startDate = missionUiModel.missionDetail.missionStartLocalDate,
                endDateTime = missionUiModel.missionDetail.missionEndLocalDateTime,
                memberList = missionVerificationUiModel.missionVerificationsResponse.missionVerifications,
                verificationTimeType = VerificationTimeType.valueOf(missionUiModel.missionDetail.timeOfDay),
                daysOfWeek = missionUiModel.missionDetail.missionDays
            )
        }

        internal fun getMissionState(
            todayLocalDateTime : LocalDateTime,
            startDate : LocalDate,
            endDateTime: LocalDateTime,
            memberList: List<MissionVerificationResponse>,
            verificationTimeType: VerificationTimeType,
            daysOfWeek : List<DayOfWeek>
        ) : MissionState {
            val todayLocalDate = todayLocalDateTime.toLocalDate()

            return if (!startDate.isAfter(todayLocalDate)) {
                if (memberList.isEmpty())
                    DELETABLE
                else {
                    if (isPassedEndTime(todayLocalDateTime, endDateTime, verificationTimeType)) {
                        POST_END
                    } else {
                        getMissionStateAsInProgress(
                            todayLocalDate,
                            todayLocalDateTime,
                            daysOfWeek,
                            verificationTimeType,
                            memberList
                        )
                    }
                }
            } else {
                getMissionStateAsPreStart(memberList.size > 1)
            }
        }

        private fun getMissionStateAsPreStart(
            isMultipleMembers: Boolean
        ): MissionState {
            return if (isMultipleMembers) PRE_START_MULTI else PRE_START_SOLO
        }

        internal fun isPassedEndTime(
            todayLocalDateTime: LocalDateTime,
            endDateTime: LocalDateTime,
            verificationTimeType: VerificationTimeType
        ): Boolean {
            val targetTime = verificationTimeType.getVerificationEndTime(endDateTime)
            return todayLocalDateTime.isAfter(targetTime)
        }

        internal fun getMissionStateAsInProgress(
            todayLocalDate: LocalDate,
            todayLocalDateTime: LocalDateTime,
            daysOfWeek: List<DayOfWeek>,
            verificationTimeType: VerificationTimeType,
            memberList : List<MissionVerificationResponse>
        ): MissionState {
            val endTime = verificationTimeType.getVerificationEndTime(todayLocalDateTime)
            if (isTodayMissionDay(todayLocalDate, daysOfWeek)) {
                when (verificationTimeType) {
                    VerificationTimeType.AFTERNOON -> {
                        if(!endTime.isAfter(VerificationTimeType.MORNING.getVerificationEndTime(todayLocalDateTime))){
                            return IN_PROGRESS_MISSION_DAY_NON_MISSION_TIME
                        }
                    }
                    else -> {
                        if(todayLocalDateTime.isAfter(endTime)){
                            return IN_PROGRESS_MISSION_DAY_CLOSED
                        }
                    }
                }
                return if(isVerifiedInMissionTime(memberList)) IN_PROGRESS_MISSION_DAY_AFTER_CONFIRM
                else IN_PROGRESS_MISSION_DAY_BEFORE_CONFIRM
            } else {
                return IN_PROGRESS_NON_MISSION_DAY
            }
        }

        internal fun isTodayMissionDay(
            todayLocalDate: LocalDate,
            missionDaysOfWeek: List<DayOfWeek>
        ): Boolean {
            return missionDaysOfWeek.contains(todayLocalDate.dayOfWeek)
        }

        internal fun isVerifiedInMissionTime(
            memberList : List<MissionVerificationResponse>
        ) : Boolean {
            if(memberList.isEmpty()) return false
            return memberList.first().imageUrl.isNotEmpty()
        }
    }
}
