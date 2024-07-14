plugins {
    alias(libs.plugins.scandroid.library)
    alias(libs.plugins.scandroid.hilt)
}

android {
    namespace = "com.ikurek.scandroid.analytics"
}

dependencies {
    implementation(projects.analytics.data.model)
}
