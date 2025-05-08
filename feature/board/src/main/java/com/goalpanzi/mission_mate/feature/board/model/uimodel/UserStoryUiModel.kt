package com.goalpanzi.mission_mate.feature.board.model.uimodel

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface UserStoryUiModel {
    data object Loading : UserStoryUiModel


}