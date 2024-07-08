plugins {
    id("com.ikurek.scandroid.library")
    id("com.ikurek.scandroid.hilt")
}

android {
    namespace = "com.ikurek.scandroid.features.settings.usecase"
}

dependencies {
    api(projects.features.settings.data.model)
    implementation(projects.core.platform)
    implementation(projects.features.settings.data.repository)
}
