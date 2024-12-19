import com.goalpanzi.mission_mate.convention.setNamespace

plugins {
    id("missionmate.android.feature")
}

android {
    setNamespace("feature.onboarding")
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)
    implementation(libs.coil.compose)
    implementation(project(":core:domain:onboarding"))
    implementation(project(":core:ui"))
    implementation(libs.google.accompanist.permissions)

    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.android.test)
}
