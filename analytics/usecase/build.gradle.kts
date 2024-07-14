plugins {
    id("com.ikurek.scandroid.library")
    id("com.ikurek.scandroid.hilt")
}

android {
    namespace = "com.ikurek.scandroid.analytics.usecase"
}

dependencies {
    api(projects.analytics.data.model)
    implementation(projects.core.platform)
    implementation(projects.analytics.data.repository)
}
