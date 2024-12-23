package com.goalpanzi.mission_mate.feature.board.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateDialog
import com.goalpanzi.mission_mate.feature.board.R

@Composable
fun InvalidMissionErrorDialog(
    onDismissRequest: () -> Unit,
    onClickOk: () -> Unit,
    modifier: Modifier = Modifier
) {
    MissionMateDialog(
        titleId = R.string.board_mission_not_exist,
        onDismissRequest = onDismissRequest,
        onClickOk = onClickOk,
        okTextId = R.string.retry,
        dialogProperties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    )
}
