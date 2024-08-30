package com.goalpanzi.mission_mate.feature.board.component.dialog

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.goalpanzi.mission_mate.core.designsystem.component.LottieImage
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateTextButton
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray2_FF4F505C
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray3_FF727484
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.core.domain.model.mission.BoardReward
import com.goalpanzi.mission_mate.feature.board.R
import com.goalpanzi.mission_mate.feature.board.model.toEventType
import com.goalpanzi.mission_mate.feature.onboarding.component.StableImage

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun BoardEventDialog(
    reward: BoardReward,
    onDismissRequest: () -> Unit,
    onClickOk: () -> Unit,
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = MissionMateTypography.title_xl_bold,
    descriptionStyle: TextStyle = MissionMateTypography.body_lg_regular,
    @StringRes okTextId: Int? = R.string.ok,
    @StringRes cancelTextId: Int? = null,
    okTextStyle: TextStyle = MissionMateTypography.body_lg_bold,
    cancelTextStyle: TextStyle = MissionMateTypography.body_lg_bold,
    shape: Shape = RoundedCornerShape(20.dp),
    dialogInnerPadding: PaddingValues = PaddingValues(
        top = 40.dp,
        bottom = 34.dp,
        start = 24.dp,
        end = 24.dp
    ),
    dialogProperties: DialogProperties = DialogProperties(
        usePlatformDefaultWidth = false
    )
) {
    val event = reward.toEventType()
    Dialog(
        properties = dialogProperties,
        onDismissRequest = onDismissRequest,
    ) {
        Box(
            modifier = Modifier.fillMaxSize().clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = onDismissRequest
            ),
            contentAlignment = Alignment.Center
        ){
            Column(
                modifier = modifier
                    .padding(horizontal = 20.dp)
                    .clip(shape)
                    .background(ColorWhite_FFFFFFFF)
                    .padding(dialogInnerPadding)
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = {}
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (event != null) {
                        stringResource(
                            id = R.string.board_mission_verification_success_dialog_reward_title,
                            stringResource(id = event.stringRes)
                        )
                    } else {
                        stringResource(id = R.string.board_mission_verification_success_dialog_title)
                    },
                    style = titleStyle,
                    textAlign = TextAlign.Center,
                    color = ColorGray1_FF404249
                )
                Text(
                    modifier = Modifier.padding(top = 12.dp, bottom = 32.dp),
                    text = if (event != null) stringResource(id = R.string.board_mission_verification_success_dialog_reward_description)
                    else stringResource(id = R.string.board_mission_verification_success_dialog_description),
                    style = descriptionStyle,
                    textAlign = TextAlign.Center,
                    color = ColorGray2_FF4F505C
                )
                StableImage(
                    drawableResId = event?.fullImageId ?: R.drawable.img_default_move,
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .size(180.dp)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (okTextId != null) {
                        MissionMateTextButton(
                            modifier = Modifier
                                .fillMaxWidth(),
                            textId = okTextId,
                            textStyle = okTextStyle,
                            onClick = onClickOk
                        )
                    }

                    if (cancelTextId != null) {
                        Text(
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null,
                                    onClick = onDismissRequest
                                ),
                            text = stringResource(id = cancelTextId),
                            style = cancelTextStyle,
                            textAlign = TextAlign.Center,
                            color = ColorGray3_FF727484
                        )
                    }
                }

            }
            LottieImage(
                modifier = Modifier.align(Alignment.Center),
                lottieRes = com.goalpanzi.mission_mate.core.designsystem.R.raw.animation_celebration
            )
        }

    }
}