plugins {
    alias(libs.plugins.scandroid.feature)
}

android {
    namespace = "com.ikurek.scandroid.features.settings"
}

dependencies {
    implementation(projects.features.settings.usecase)
}
