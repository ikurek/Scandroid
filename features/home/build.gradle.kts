plugins {
    alias(libs.plugins.scandroid.feature)
    alias(libs.plugins.scandroid.detekt)
}

android {
    namespace = "com.ikurek.scandroid.features.home"
}

dependencies {
    implementation(projects.core.translations)
    implementation(projects.features.createscan)
    implementation(projects.features.savedscans)
}