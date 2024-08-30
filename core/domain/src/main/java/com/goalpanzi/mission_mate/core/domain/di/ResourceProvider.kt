package com.goalpanzi.mission_mate.core.domain.di

// TODO 다른 모듈로 옮기기

//@Singleton
//class ResourceProvider @Inject constructor(
//    @ApplicationContext private val context: Context
//) {
//    fun getString(@StringRes stringResId: Int): String {
//        return context.getString(stringResId)
//    }
//
//    fun getIntArray(@ArrayRes arrayResId: Int): Array<Int> {
//        return context.resources.getIntArray(arrayResId).toTypedArray()
//    }
//
//    fun getDrawableArray(@ArrayRes arrayResId: Int): TypedArray {
//        return context.resources.obtainTypedArray(arrayResId)
//    }
//
//    fun getStringArray(@ArrayRes arrayResId: Int): Array<String> {
//        return context.resources.getStringArray(arrayResId)
//    }
//}