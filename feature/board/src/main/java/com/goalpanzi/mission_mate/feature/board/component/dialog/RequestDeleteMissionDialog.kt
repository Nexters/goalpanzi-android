package com.goalpanzi.mission_mate.feature.board.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateDialog
import com.goalpanzi.mission_mate.feature.board.R

@Composable
fun RequestDeleteMissionDialog(
    onDismissRequest: () -> Unit,
    onClickOk: () -> Unit,
    modifier: Modifier = Modifier
) {
    MissionMateDialog(
        modifier = modifier,
        titleId = R.string.board_request_delete_title,
        descriptionId = R.string.board_request_delete_description,
        onDismissRequest = onDismissRequest,
        onClickOk = onClickOk,
        okTextId = R.string.ok,
        cancelTextId = R.string.cancel
    )
}

@Preview
@Composable
private fun PreviewRequestDeleteMissionDialog() {
    RequestDeleteMissionDialog(
        onClickOk = {},
        onDismissRequest = {}
    )

}
