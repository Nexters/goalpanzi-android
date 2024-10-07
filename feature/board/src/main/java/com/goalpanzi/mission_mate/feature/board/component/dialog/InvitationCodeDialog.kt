package com.goalpanzi.mission_mate.feature.board.component.dialog

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateDialog
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray2_FF4F505C
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorOrange_FFFF5732
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.feature.board.R
import com.goalpanzi.mission_mate.core.ui.component.InvitationCodeTextField
import com.goalpanzi.mission_mate.feature.onboarding.util.styledTextWithHighlights

@Composable
fun InvitationCodeDialog(
    code : String,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    minMemberSize : Int = 2,
    maxMemberSize : Int = 10,
    titleStyle: TextStyle = MissionMateTypography.title_xl_bold,
    descriptionStyle: TextStyle = MissionMateTypography.body_lg_regular,
    okTextStyle: TextStyle = MissionMateTypography.body_lg_bold,
    cancelTextStyle: TextStyle = MissionMateTypography.body_lg_bold
) {
    if(code.length != 4) return
    val context = LocalContext.current
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    MissionMateDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        onClickOk = {
            clipboardManager.setPrimaryClip(
                ClipData.newPlainText("MissionMate 초대 코드", code)
            )
        },
        okTextId = R.string.board_invitation_dialog_copy,
        cancelTextId = R.string.close,
        okTextStyle = okTextStyle,
        cancelTextStyle = cancelTextStyle
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = styledTextWithHighlights(
                    text = stringResource(id = R.string.board_invitation_dialog_title),
                    colorTargetTexts = listOf(stringResource(id = R.string.board_invitation_dialog_title_color_target)),
                    targetTextColor = ColorOrange_FFFF5732,
                    textColor = ColorGray1_FF404249
                ),
                style = titleStyle,
                textAlign = TextAlign.Center,
                color = ColorGray1_FF404249
            )
            Text(
                modifier = Modifier.padding(top = 4.dp, bottom = 24.dp),
                text = stringResource(id = R.string.board_invitation_dialog_description,minMemberSize,maxMemberSize),
                style = descriptionStyle,
                textAlign = TextAlign.Center,
                color = ColorGray2_FF4F505C
            )

            Row(
                modifier = Modifier.padding(bottom = 32.dp).wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InvitationCodeTextField(
                    modifier = Modifier.weight(1f).aspectRatio(1f),
                    text = "${code[0]}",
                    onValueChange = {},
                    readOnly = true
                )
                InvitationCodeTextField(
                    modifier = Modifier.weight(1f).aspectRatio(1f),
                    text = "${code[1]}",
                    onValueChange = {},
                    readOnly = true
                )
                InvitationCodeTextField(
                    modifier = Modifier.weight(1f).aspectRatio(1f),
                    text = "${code[2]}",
                    onValueChange = {},
                    readOnly = true
                )
                InvitationCodeTextField(
                    modifier = Modifier.weight(1f).aspectRatio(1f),
                    text = "${code[3]}",
                    onValueChange = {},
                    readOnly = true
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewInvitationCodeDialog() {
    InvitationCodeDialog(
        code = "ABCD",
        onDismissRequest = {}
    )
}
