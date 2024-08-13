package com.goalpanzi.mission_mate.feature.board.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray1_FF404249
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorGray5_FFF5F6F9
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorOrange_FFFF5732
import com.goalpanzi.mission_mate.core.designsystem.theme.ColorWhite_FFFFFFFF
import com.goalpanzi.mission_mate.core.designsystem.theme.MissionMateTypography
import com.goalpanzi.mission_mate.core.designsystem.theme.OrangeGradient_FFFF5F3C_FFFFAE50
import com.goalpanzi.mission_mate.feature.board.model.UserStory

@Composable
fun BoardTopStory(
    userList: List<UserStory>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(top = 10.dp, bottom = 14.dp, start = 24.dp, end = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        if (userList.isNotEmpty()) {
            items(userList) { userStory ->
                UserStoryItem(
                    userStory = userStory
                )
            }
        } else {
            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(98.dp)
                )
            }
        }
    }
}

@Composable
fun UserStoryItem(
    userStory: UserStory,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(98.dp)
            .width(70.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(id = userStory.characterType.imageId),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 8.dp)
                .height(64.dp)
                .width(64.dp)
                .then(
                    if (userStory.isVerified) Modifier.border(
                        3.dp, OrangeGradient_FFFF5F3C_FFFFAE50,
                        CircleShape
                    )
                    else Modifier.border(
                        3.dp, ColorWhite_FFFFFFFF,
                        CircleShape
                    )
                )
                .paint(
                    painter = painterResource(userStory.characterType.backgroundId),
                    contentScale = ContentScale.FillWidth
                )
                .padding(5.dp),
        )
        if (userStory.isMe) {
            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .background(color = ColorOrange_FFFF5732, shape = RoundedCornerShape(16.dp))
                    .padding(vertical = 1.dp, horizontal = 14.dp)
                    .align(Alignment.TopCenter),
                text = "나",
                color = ColorWhite_FFFFFFFF,
                style = MissionMateTypography.body_md_bold

            )
        }
        Text(
            modifier = Modifier
                .wrapContentHeight()
                .widthIn(min = 70.dp)
                .background(
                    color = ColorGray5_FFF5F6F9.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(20.dp)
                )
                .border(
                    1.dp,
                    color = ColorGray5_FFF5F6F9.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(vertical = 1.dp, horizontal = 4.dp)
                .align(Alignment.BottomCenter),
            text = userStory.nickname,
            textAlign = TextAlign.Center,
            color = ColorGray1_FF404249,
            style = MissionMateTypography.body_sm_regular

        )
//        Box(
//            modifier = modifier
//                .size(64.dp)
//                .paint(
//                    painter = painterResource(id = R.drawable.img_sea)
//                )
//                //.background(color = userStory.characterType.color, shape = CircleShape)
//                //.border(1.dp, color = if(userStory.isVerified)  )
//                // .align(Alignment.CenterHorizontally)
//        ) {
//         //   CharacterLargeImage(imageResId = it.imageResId)
//        }
        //  StableImage(drawableResId = )
    }
}

