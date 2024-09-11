import com.goalpanzi.mission_mate.convention.configureHiltAndroid
import com.goalpanzi.mission_mate.convention.configureKotlinAndroid

plugins {
    id("com.android.application")
}

configureKotlinAndroid()
configureHiltAndroid()
