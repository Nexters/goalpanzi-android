package com.goalpanzi.mission_mate.feature.board.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.goalpanzi.mission_mate.core.domain.mission.model.BoardReward
import com.goalpanzi.mission_mate.feature.board.R

data class BoardEventItem(
    val index: Int,
    val eventType: EventType?
)

enum class EventType(
    @DrawableRes val imageId: Int,
    @StringRes val stringRes: Int,
    @DrawableRes val fullImageId: Int,
) {
    ORANGE(
        imageId = R.drawable.img_orange,
        stringRes = R.string.board_mission_event_orange,
        fullImageId = R.drawable.img_orange_full
    ),
    CANOLA_FLOWER(
        imageId = R.drawable.img_flower,
        stringRes = R.string.board_mission_event_canola_flower,
        fullImageId = R.drawable.img_canola_flower_full
    ),
    DOLHARUBANG(
        imageId = R.drawable.img_stone,
        stringRes = R.string.board_mission_event_dolharubang,
        fullImageId = R.drawable.img_dolharubang_full
    ),
    HORSE_RIDING(
        imageId = R.drawable.img_horse,
        stringRes = R.string.board_mission_event_horse_riding,
        fullImageId = R.drawable.img_horse_riding_full
    ),
    HALLA_MOUNTAIN(
        imageId = R.drawable.img_mountain,
        stringRes = R.string.board_mission_event_halla_mountain,
        fullImageId = R.drawable.img_halla_mountain_full
    ),
    WATERFALL(
        imageId = R.drawable.img_waterfall,
        stringRes = R.string.board_mission_event_waterfall,
        fullImageId = R.drawable.img_waterfall_full
    ),
    BLACK_PIG(
        imageId = R.drawable.img_pig,
        stringRes = R.string.board_mission_event_black_pig,
        fullImageId = R.drawable.img_black_pig_full
    ),
    SUNRISE(
        imageId = R.drawable.img_bong,
        stringRes = R.string.board_mission_event_sunrise,
        fullImageId = R.drawable.img_sunrise_full
    ),
    GREEN_TEA_FIELD(
        imageId = R.drawable.img_green_tea,
        stringRes = R.string.board_mission_event_green_tea_field,
        fullImageId = R.drawable.img_green_tea_field_full
    ),
    BEACH(
        imageId = R.drawable.img_sea,
        stringRes = R.string.board_mission_event_beach,
        fullImageId = R.drawable.img_beach_full
    )
}


fun BoardReward.toEventType(): EventType? {
    return EventType.entries.find { it.name == this.name }
}
