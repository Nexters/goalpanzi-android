import com.goalpanzi.mission_mate.convention.configureCoroutineAndroid
import com.goalpanzi.mission_mate.convention.configureHiltAndroid
import com.goalpanzi.mission_mate.convention.configureKotlinAndroid

plugins {
    id("com.android.library")
}

configureKotlinAndroid()
configureCoroutineAndroid()
configureHiltAndroid()
