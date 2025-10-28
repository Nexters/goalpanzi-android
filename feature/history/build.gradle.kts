import com.goalpanzi.mission_mate.convention.setNamespace

plugins {
    id("missionmate.android.feature")
}

android {
    setNamespace("feature.history")
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.coroutines)
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.android.test)

    implementation(libs.coil.compose)

}
