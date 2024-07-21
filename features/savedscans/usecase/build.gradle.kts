plugins {
    id("com.ikurek.scandroid.library")
    id("com.ikurek.scandroid.hilt")
}

android {
    namespace = "com.ikurek.scandroid.features.savedscans.usecase"
}

dependencies {
    api(projects.features.savedscans.data.model)
    implementation(projects.analytics)
    implementation(projects.features.savedscans.data.repository)
}
