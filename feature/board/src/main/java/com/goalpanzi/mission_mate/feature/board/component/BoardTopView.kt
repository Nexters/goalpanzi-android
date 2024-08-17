package com.goalpanzi.mission_mate.feature.board.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.component.MissionMateTopAppBar
import com.goalpanzi.mission_mate.core.designsystem.theme.component.NavigationType
import com.goalpanzi.mission_mate.feature.board.R
import com.goalpanzi.mission_mate.feature.board.model.UserStory
import com.goalpanzi.mission_mate.feature.onboarding.component.StableImage


@Composable
fun BoardTopView(
    title: String,
    isAddingUserEnabled: Boolean,
    userList: List<UserStory>,
    onClickFlag: () -> Unit,
    onClickAddUser: () -> Unit,
    onClickSetting: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(ColorWhite_FFFFFFFF.copy(alpha = 0.5f))
            .statusBarsPadding()
    ) {
        MissionMateTopAppBar(
            modifier = modifier,
            navigationType = NavigationType.NONE,
            title = title,
            leftActionButtons = {
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
            modifier = Modifier.padding(top = 56.dp),
            userList = userList
        )
        if (!isAddingUserEnabled) {
            // datastore 조건 추가
            StableImage(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 43.dp,top = 48.dp)
                    .width(161.dp),
                drawableResId = R.drawable.img_tooltip_mission_invitation_code,
                contentScale = ContentScale.Crop
            )
        } else {
            // datastore 조건 추가
            StableImage(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 8.dp, top = 48.dp)
                    .width(161.dp),
                drawableResId = R.drawable.img_tooltip_mission_detail,
                contentScale = ContentScale.Crop
            )
        }

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
