plugins {
    alias(libs.plugins.scandroid.feature)
}

android {
    namespace = "com.ikurek.scandroid.features.search"
}

dependencies {
    implementation(projects.features.search.usecase)
}
