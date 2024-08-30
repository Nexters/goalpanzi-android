plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.ksp)
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.bundles.test)
    implementation(libs.coroutines.core)
    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)
}