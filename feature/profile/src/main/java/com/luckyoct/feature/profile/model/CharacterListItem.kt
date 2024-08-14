package com.luckyoct.feature.profile.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.luckyoct.core.model.CharacterType
import com.luckyoct.feature.profile.R

data class CharacterListItem(
    val type: CharacterType,
    @DrawableRes val imageResId: Int,
    @StringRes val nameResId: Int,
    val isSelected: Boolean = false,
    @DrawableRes val backgroundResId: Int
) {
    companion object {
        fun createDefaultList() = listOf(
            CharacterListItem(
                type = CharacterType.RABBIT,
                imageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_rabbit_selected,
                nameResId = R.string.rabbit_name,
                backgroundResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_rabbit
            ),
            CharacterListItem(
                type = CharacterType.CAT,
                imageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_cat_selected,
                nameResId = R.string.cat_name,
                backgroundResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_cat
            ),
            CharacterListItem(
                type = CharacterType.DOG,
                imageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_dog_selected,
                nameResId = R.string.dog_name,
                backgroundResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_dog
            ),
            CharacterListItem(
                type = CharacterType.PANDA,
                imageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_panda_selected,
                nameResId = R.string.panda_name,
                backgroundResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_panda
            ),
            CharacterListItem(
                type = CharacterType.BEAR,
                imageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_bear_selected,
                nameResId = R.string.bear_name,
                backgroundResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_bear
            ),
            CharacterListItem(
                type = CharacterType.BIRD,
                imageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_bird_selected,
                nameResId = R.string.bird_name,
                backgroundResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_bird
            )
        )
    }
}
