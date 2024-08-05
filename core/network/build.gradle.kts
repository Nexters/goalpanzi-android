import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.kotlin.plugin.serialization)
}

android {
    namespace = "com.goalpanzi.mission_mate.core.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", getMissionMateBaseUrl())
        }
        release {
            buildConfigField("String", "BASE_URL", getMissionMateBaseUrl())
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
}

dependencies {

    implementation(libs.bundles.test)
    implementation(libs.bundles.network)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.bundles.coroutines)

    ksp(libs.hilt.compiler)
    implementation(libs.hilt.android)

    implementation(project(":core:model"))
    implementation(project(":core:datastore"))
}

fun getMissionMateBaseUrl(): String {
    return gradleLocalProperties(rootDir, providers).getProperty("MISSION_MATE_BASE_URL") ?: ""
}
