import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.goalpanzi.mission_mate.convention.setNamespace
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("missionmate.android.feature")
}

android {
    setNamespace("feature.login")
    buildTypes {
        getByName("debug") {
            buildConfigField("String", "CREDENTIAL_WEB_CLIENT_ID", getCredentialClientId())
        }
        getByName("release") {
            buildConfigField("String", "CREDENTIAL_WEB_CLIENT_ID", getCredentialClientId())
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.coroutines)
    implementation(libs.credentials)
    implementation(libs.credentials.auth)
    implementation(libs.google.id)

    testImplementation(libs.bundles.test)
}

fun getCredentialClientId(): String {
    return gradleLocalProperties(rootDir, providers).getProperty("CREDENTIAL_WEB_CLIENT_ID") ?: ""
}
