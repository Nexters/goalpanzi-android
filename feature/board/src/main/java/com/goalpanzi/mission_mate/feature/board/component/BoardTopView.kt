package com.goalpanzi.mission_mate.feature.board.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.component.MissionMateTopAppBar
import com.goalpanzi.mission_mate.core.designsystem.theme.component.NavigationType
import com.goalpanzi.mission_mate.feature.board.model.UserStory


@Composable
fun BoardTopView(
    title: String,
    isAddingUserEnabled: Boolean,
    userList: List<UserStory>,
    onClickFlag: () -> Unit,
    onClickAddUser: () -> Unit,
    onClickSetting: () -> Unit,
    modifier: Modifier = Modifier,
    isVisibleFlagButton: Boolean = false
) {
    Column(
        modifier = modifier
            .background(ColorWhite_FFFFFFFF.copy(alpha = 0.5f))
            .statusBarsPadding()
    ) {
        MissionMateTopAppBar(
            modifier = modifier,
            navigationType = NavigationType.NONE,
            title = title,
            leftActionButtons = {
                if (isVisibleFlagButton) {
                    IconButton(
                        onClick = onClickFlag,
                        modifier = Modifier.wrapContentSize()
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = com.goalpanzi.mission_mate.core.designsystem.R.drawable.ic_flag),
                            contentDescription = "",
                            tint = ColorGray1_FF404249
                        )
                    }
                }
            },
            rightActionButtons = {
                BoardTopViewRightActionButtons(
                    isAddingUserEnabled = isAddingUserEnabled,
                    onClickAddUser = onClickAddUser,
                    onClickSetting = onClickSetting
                )
            },
            containerColor = Color.Transparent
        )
        BoardTopStory(
            userList = userList
        )
    }
}

@Composable
fun BoardTopViewRightActionButtons(
    isAddingUserEnabled: Boolean,
    onClickAddUser: () -> Unit,
    onClickSetting: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isAddingUserEnabled) {
            IconButton(
                onClick = onClickAddUser,
                modifier = Modifier.wrapContentSize()
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = com.goalpanzi.mission_mate.core.designsystem.R.drawable.ic_add_user),
                    contentDescription = "",
                    tint = ColorGray1_FF404249
                )
            }
        }
        IconButton(
            onClick = onClickSetting,
            modifier = Modifier.wrapContentSize()
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = com.goalpanzi.mission_mate.core.designsystem.R.drawable.ic_setting),
                contentDescription = "",
                tint = ColorGray1_FF404249
            )
        }
    }
}
