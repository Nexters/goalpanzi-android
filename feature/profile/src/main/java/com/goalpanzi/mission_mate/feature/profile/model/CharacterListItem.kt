package com.goalpanzi.mission_mate.feature.profile.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.goalpanzi.mission_mate.core.domain.common.model.user.CharacterType
import com.luckyoct.feature.profile.R

data class CharacterListItem(
    val type: CharacterType,
    @DrawableRes val selectedImageResId: Int,
    @DrawableRes val defaultImageResId: Int,
    @StringRes val nameResId: Int,
    val isSelected: Boolean = false,
    @DrawableRes val backgroundResId: Int
) {
    companion object {
        fun createDefaultList() = listOf(
            CharacterListItem(
                type = CharacterType.RABBIT,
                selectedImageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_rabbit_selected,
                defaultImageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_rabbit_default,
                nameResId = R.string.rabbit_name,
                backgroundResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_rabbit
            ),
            CharacterListItem(
                type = CharacterType.CAT,
                selectedImageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_cat_selected,
                defaultImageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_cat_default,
                nameResId = R.string.cat_name,
                backgroundResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_cat
            ),
            CharacterListItem(
                type = CharacterType.DOG,
                selectedImageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_dog_selected,
                defaultImageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_dog_default,
                nameResId = R.string.dog_name,
                backgroundResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_dog
            ),
            CharacterListItem(
                type = CharacterType.PANDA,
                selectedImageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_panda_selected,
                defaultImageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_panda_default,
                nameResId = R.string.panda_name,
                backgroundResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_panda
            ),
            CharacterListItem(
                type = CharacterType.BEAR,
                selectedImageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_bear_selected,
                defaultImageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_bear_default,
                nameResId = R.string.bear_name,
                backgroundResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_bear
            ),
            CharacterListItem(
                type = CharacterType.BIRD,
                selectedImageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_bird_selected,
                defaultImageResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.img_bird_default,
                nameResId = R.string.bird_name,
                backgroundResId = com.goalpanzi.mission_mate.core.designsystem.R.drawable.background_bird
            )
        )
    }
}
