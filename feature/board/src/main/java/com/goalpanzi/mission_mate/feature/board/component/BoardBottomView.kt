package com.goalpanzi.mission_mate.feature.board.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateButtonType
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateTextButton
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray2_FF4F505C
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.feature.board.R
import com.goalpanzi.mission_mate.feature.board.model.MissionDetail
import com.goalpanzi.mission_mate.feature.board.model.MissionState
import com.goalpanzi.mission_mate.feature.onboarding.component.StableImage
import com.goalpanzi.mission_mate.feature.onboarding.model.VerificationTimeType

@Composable
fun BoardBottomView(
    onClickButton: () -> Unit,
    missionDetail: MissionDetail,
    missionState: MissionState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .navigationBarsPadding()
            .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
            .background(ColorWhite_FFFFFFFF.copy(alpha = 0.7f))
            .padding(top = 16.dp, bottom = 36.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StableImage(drawableResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.ic_time)
            Text(
                text = missionDetail.missionDaysOfWeekTextLocale.joinToString(" ") + " | " + when(VerificationTimeType.valueOf(missionDetail.timeOfDay)){
                    VerificationTimeType.MORNING -> stringResource(id = R.string.board_verification_am_time_limit)
                    VerificationTimeType.AFTERNOON -> stringResource(id = R.string.board_verification_pm_time_limit)
                    VerificationTimeType.EVERYDAY -> stringResource(id = R.string.board_verification_all_day_time_limit)
                },
                style = MissionMateTypography.body_lg_bold,
                color = ColorGray2_FF4F505C
            )
        }
        MissionMateTextButton(
            modifier = Modifier.fillMaxWidth(),
            textId = when(missionState){
                MissionState.IN_PROGRESS_NON_MISSION_DAY -> R.string.board_verification_not_day
                MissionState.IN_PROGRESS_MISSION_DAY_AFTER_CONFIRM -> R.string.board_verification_done
                MissionState.IN_PROGRESS_MISSION_DAY_CLOSED -> R.string.board_verification_closed
                MissionState.IN_PROGRESS_MISSION_DAY_NON_MISSION_TIME -> R.string.board_verification_not_time
                else -> R.string.board_verification
            },
            buttonType = if (missionState.enabledVerification()) MissionMateButtonType.ACTIVE else MissionMateButtonType.DISABLED,
            onClick = onClickButton
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewBoardBottomView() {
    BoardBottomView(
        missionState = MissionState.POST_END,
        missionDetail = MissionDetail(
            missionId = 1,
            hostMemberId = 2,
            description = "convallis",
            missionStartDate = "mnesarchum",
            missionEndDate = "congue",
            timeOfDay = "MORNING",
            missionDays = listOf(),
            boardCount = 12,
            invitationCode = "ABDC"
        ),
        onClickButton = {}
    )
}