plugins {
    alias(libs.plugins.scandroid.feature)
}

android {
    namespace = "com.ikurek.scandroid.features.scandetails"
}

dependencies {
    implementation(projects.features.savedscans.usecase)
}
