plugins {
    alias(libs.plugins.scandroid.feature)
}

android {
    namespace = "com.ikurek.scandroid.features.home"
}

dependencies {
    implementation(projects.features.createscan)
    implementation(projects.features.savedscans)
}