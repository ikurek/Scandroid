plugins {
    id("com.ikurek.scandroid.library")
    id("com.ikurek.scandroid.hilt")
}

android {
    namespace = "com.ikurek.scandroid.analytics.data.repository"
}

dependencies {
    implementation(projects.core.datastore)
    implementation(projects.analytics.data.model)
}
