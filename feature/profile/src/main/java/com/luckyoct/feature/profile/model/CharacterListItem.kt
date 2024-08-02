package com.luckyoct.feature.profile.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color

data class CharacterListItem(
    @DrawableRes val imageResId: Int,
    @StringRes val nameResId: Int,
    val isSelected: Boolean,
    val backgroundColor: Color
)
