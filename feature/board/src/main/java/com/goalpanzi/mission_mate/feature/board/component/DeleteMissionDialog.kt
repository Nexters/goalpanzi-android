package com.goalpanzi.mission_mate.feature.board.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateDialog
import com.goalpanzi.mission_mate.feature.board.R

@Composable
fun DeleteMissionDialog(
    onDismissRequest: () -> Unit,
    onClickOk: () -> Unit,
    modifier: Modifier = Modifier
) {
    MissionMateDialog(
        titleId = R.string.board_delete_title,
        descriptionId = R.string.board_delete_description,
        onDismissRequest = onDismissRequest,
        onClickOk = onClickOk,
        okTextId = R.string.ok
    )
}