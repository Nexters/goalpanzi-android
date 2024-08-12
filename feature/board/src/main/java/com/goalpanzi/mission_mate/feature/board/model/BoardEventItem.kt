package com.goalpanzi.mission_mate.feature.board.model

import androidx.annotation.DrawableRes
import com.goalpanzi.mission_mate.feature.board.R

data class BoardEventItem(
    val index: Int,
    val eventType: EventType
)

enum class EventType(
    @DrawableRes val imageId: Int
) {
    Orange(imageId = R.drawable.img_orange),
    Flower(imageId = R.drawable.img_flower),
    Stone(imageId = R.drawable.img_stone),
    Horse(imageId = R.drawable.img_horse),
    Mountain(imageId = R.drawable.img_mountain),
    Waterfall(imageId = R.drawable.img_waterfall),
    Pig(imageId = R.drawable.img_pig),
    Bong(imageId = R.drawable.img_bong),
    GreenTea(imageId = R.drawable.img_green_tea),
    Sea(imageId = R.drawable.img_sea)
}