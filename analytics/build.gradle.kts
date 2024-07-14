plugins {
    alias(libs.plugins.scandroid.library)
    alias(libs.plugins.scandroid.hilt)
}

android {
    namespace = "com.ikurek.scandroid.analytics"
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(projects.analytics.data.model)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.performance)
}
