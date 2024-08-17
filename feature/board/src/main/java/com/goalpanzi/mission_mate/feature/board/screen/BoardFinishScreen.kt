package com.goalpanzi.mission_mate.feature.board.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateButtonType
import com.goalpanzi.mission_mate.core.designsystem.component.MissionMateTextButton
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.core.designsystem.theme.component.MissionMateTopAppBar
import com.goalpanzi.mission_mate.core.designsystem.theme.component.NavigationType
import com.goalpanzi.mission_mate.feature.board.R
import com.goalpanzi.mission_mate.feature.board.model.Character
import com.goalpanzi.mission_mate.feature.board.model.toCharacter
import com.goalpanzi.mission_mate.feature.onboarding.component.StableImage

@Composable
fun BoardFinishScreen(
    character : String,
    rank : Int,
    onClickSetting: () -> Unit,
    onClickOk : () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = ColorWhite_FFFFFFFF)
    ) {
        StableImage(
            modifier = Modifier.fillMaxWidth(),
            drawableResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_jeju,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = modifier
                .statusBarsPadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MissionMateTopAppBar(
                modifier = modifier,
                navigationType = NavigationType.NONE,
                rightActionButtons = {
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
                },
                containerColor = Color.Transparent
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.BottomCenter
            ){
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    StableImage(
                        modifier = Modifier.fillMaxWidth(),
                        drawableResId = R.drawable.img_mission_finish,
                        contentScale = ContentScale.Crop
                    )
                    StableImage(
                        modifier = Modifier.fillMaxWidth(212f/390f).aspectRatio(1f),
                        drawableResId = Character.valueOf(character).imageId
                    )
                }

            }
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(id = R.string.board_finish_rank, rank),
                color = ColorGray1_FF404249,
                style = MissionMateTypography.heading_xl_bold
            )
            Text(
                modifier = Modifier.padding(top = 20.dp, bottom = 4.dp),
                text = stringResource(id = R.string.board_finish_description),
                color = ColorGray1_FF404249,
                style = MissionMateTypography.title_xl_bold
            )
            Text(
                text = stringResource(id = R.string.board_finish_sub_description),
                color = ColorGray1_FF404249,
                style = MissionMateTypography.body_xl_regular,
                textAlign = TextAlign.Center
            )
            MissionMateTextButton(
                modifier = Modifier
                    .padding(bottom = 36.dp, start = 24.dp, end = 24.dp, top = 68.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                buttonType = MissionMateButtonType.ACTIVE,
                textId = com.goalpanzi.mission_mate.feature.onboarding.R.string.start,
                onClick = onClickOk
            )
        }
    }

}

@Preview
@Composable
private fun PreviewBoardFinishScreen() {
    BoardFinishScreen(
        character = "RABBIT",
        rank = 10,
        onClickSetting = {},
        onClickOk = {}
    )

}