plugins {
    id("com.ikurek.scandroid.library")
    id("com.ikurek.scandroid.detekt")
    id("com.ikurek.scandroid.hilt")
}

android {
    namespace = "com.ikurek.scandroid.features.createscan.data.repository"
}

dependencies {
    implementation(projects.common.coroutines)
    implementation(projects.core.filestore)
    implementation(projects.features.createscan.data.model)
}