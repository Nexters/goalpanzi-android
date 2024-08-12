package com.goalpanzi.mission_mate.feature.board.model

import androidx.annotation.DrawableRes
import com.luckyoct.core.model.request.CharacterType

enum class Character(
    @DrawableRes val imageId: Int,
    @DrawableRes val backgroundId: Int,
) {
    CAT(
        imageId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_cat_selected,
        backgroundId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_cat
    ),
    DOG(
        imageId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_dog_selected,
        backgroundId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_dog
    ),
    RABBIT(
        imageId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_rabbit_selected,
        backgroundId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_rabbit
    ),
    BEAR(
        imageId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_bear_selected,
        backgroundId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_bear
    ),
    PANDA(
        imageId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_panda_selected,
        backgroundId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_panda
    ),
    BIRD(
        imageId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_bird_selected,
        backgroundId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_bird
    )
}

fun CharacterType.toCharacter() : Character {
    return Character.entries
        .find { it.name == this.name } ?: Character.RABBIT
}
