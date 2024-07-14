plugins {
    alias(libs.plugins.scandroid.library)
    alias(libs.plugins.scandroid.hilt)
}

android {
    namespace = "com.ikurek.scandroid.common.coroutines"
}

dependencies {
    api(libs.kotlinx.coroutines.android)
}
