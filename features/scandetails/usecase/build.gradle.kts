plugins {
    id("com.ikurek.scandroid.library")
    id("com.ikurek.scandroid.hilt")
}

android {
    namespace = "com.ikurek.scandroid.features.scandetails.usecase"
}

dependencies {
    implementation(projects.core.platform)
    implementation(projects.features.savedscans.data.model)
}
