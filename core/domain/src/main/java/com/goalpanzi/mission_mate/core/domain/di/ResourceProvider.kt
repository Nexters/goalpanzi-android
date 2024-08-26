package com.goalpanzi.mission_mate.core.domain.di

import android.content.Context
import android.content.res.TypedArray
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }

    fun getIntArray(@ArrayRes arrayResId: Int): Array<Int> {
        return context.resources.getIntArray(arrayResId).toTypedArray()
    }

    fun getDrawableArray(@ArrayRes arrayResId: Int): TypedArray {
        return context.resources.obtainTypedArray(arrayResId)
    }

    fun getStringArray(@ArrayRes arrayResId: Int): Array<String> {
        return context.resources.getStringArray(arrayResId)
    }
}