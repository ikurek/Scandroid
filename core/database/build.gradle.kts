plugins {
    alias(libs.plugins.scandroid.library)
    alias(libs.plugins.scandroid.hilt)
}

android {
    namespace = "com.ikurek.scandroid.core.database"
}

dependencies {
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
}
