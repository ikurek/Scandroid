plugins {
    alias(libs.plugins.scandroid.feature)
}

android {
    namespace = "com.ikurek.scandroid.features.home"
}

dependencies {
    implementation(projects.analytics)
    implementation(projects.features.savedscans)
    implementation(projects.features.search)
}
