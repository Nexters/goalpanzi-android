import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "com.goalpanzi.mission_mate"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.goalpanzi.mission_mate"
        minSdk = 26
        targetSdk = 34
        versionCode = 5
        versionName = "1.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file("./mission-mate-keystore.jks")
            storePassword = gradleLocalProperties(rootDir, providers).getProperty("SIGNING_STORE_PASSWORD")
            keyAlias =  gradleLocalProperties(rootDir, providers).getProperty("SIGNING_KEY_ALIAS")
            keyPassword =  gradleLocalProperties(rootDir, providers).getProperty("SIGNING_KEY_PASSWORD")
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isDebuggable = false
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    ksp(libs.hilt.compiler)
    implementation(libs.hilt.android)
    implementation(platform(libs.firebase.bom))

    implementation(project(":feature:main"))
    implementation(project(":feature:login"))
    implementation(project(":core:designsystem"))

    implementation(project(":core:data:auth"))
    implementation(project(":core:data:common"))
    implementation(project(":core:data:mission"))
    implementation(project(":core:data:onboarding"))
    implementation(project(":core:data:setting"))
    implementation(project(":core:data:user"))
}
