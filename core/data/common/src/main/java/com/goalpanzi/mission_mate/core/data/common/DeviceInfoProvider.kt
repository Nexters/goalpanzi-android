package com.goalpanzi.mission_mate.core.data.common

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import javax.inject.Inject

class DeviceInfoProvider @Inject constructor(
    private val context: Context
) {
    @SuppressLint("HardwareIds")
    fun getDeviceSSAID(): String {
        return Settings.Secure.getString(context.contentResolver,Settings.Secure.ANDROID_ID)
    }
}
