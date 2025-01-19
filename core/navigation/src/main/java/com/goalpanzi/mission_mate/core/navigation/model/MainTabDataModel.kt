package com.goalpanzi.mission_mate.core.navigation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
sealed class MainTabDataModel : Parcelable {
    @Serializable
    data class Mission(
        val isAfterProfileCreate: Boolean = false
    ) : MainTabDataModel()
    @Serializable
    data object History : MainTabDataModel()
    @Serializable
    data object Setting : MainTabDataModel()
}
