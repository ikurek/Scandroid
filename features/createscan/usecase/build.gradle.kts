plugins {
    id("com.ikurek.scandroid.library")
    id("com.ikurek.scandroid.detekt")
    id("com.ikurek.scandroid.hilt")
}

android {
    namespace = "com.ikurek.scandroid.features.createscan.usecase"
}

dependencies {
    api(projects.features.createscan.data.model)
    implementation(projects.features.createscan.data.repository)
}