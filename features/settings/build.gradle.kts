plugins {
    alias(libs.plugins.scandroid.feature)
}

android {
    namespace = "com.ikurek.scandroid.features.settings"
}

dependencies {
    implementation(projects.analytics.usecase)
    implementation(projects.features.settings.usecase)
}
