plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "missionmate.android.application"
            implementationClass = "MissionmateAndroidApplicationPlugin"
        }
        register("androidLibrary") {
            id = "missionmate.android.library"
            implementationClass = "MissionmateAndroidLibrary"
        }
        register("androidHilt") {
            id = "missionmate.android.hilt"
            implementationClass = "com.goalpanzi.mission_mate.convention.HiltAndroidPlugin"
        }
    }
}