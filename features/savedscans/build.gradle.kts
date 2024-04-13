plugins {
    alias(libs.plugins.scandroid.feature)
}

android {
    namespace = "com.ikurek.scandroid.features.savedscans"
}

dependencies {
    implementation(projects.features.savedscans.usecase)
}
