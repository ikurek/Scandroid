plugins {
    alias(libs.plugins.scandroid.library)
    alias(libs.plugins.scandroid.hilt)
}

android {
    namespace = "com.ikurek.scandroid.core.datastore"
}

dependencies {
    implementation(libs.androidx.datastore.preferences)
}
