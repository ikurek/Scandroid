plugins {
    id("com.ikurek.scandroid.library")
    id("com.ikurek.scandroid.hilt")
}

android {
    namespace = "com.ikurek.scandroid.features.savedscans.data.repository"
}

dependencies {
    implementation(projects.analytics)
    implementation(projects.common.coroutines)
    implementation(projects.core.database)
    implementation(projects.core.filestore)
    implementation(projects.features.savedscans.data.model)
}
