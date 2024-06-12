plugins {
    id("com.ikurek.scandroid.library")
    id("com.ikurek.scandroid.hilt")
}

android {
    namespace = "com.ikurek.scandroid.features.settings.data.repository"
}

dependencies {
    implementation(projects.core.datastore)
    implementation(projects.features.settings.data.model)
}
