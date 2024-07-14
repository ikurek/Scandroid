plugins {
    alias(libs.plugins.scandroid.feature)
}

android {
    namespace = "com.ikurek.scandroid.features.home"
}

dependencies {
    implementation(projects.analytics)
    implementation(projects.core.translations)
    implementation(projects.features.createscan)
    implementation(projects.features.savedscans)
    implementation(projects.features.scandetails)
    implementation(projects.features.settings)
}
