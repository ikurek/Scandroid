plugins {
    alias(libs.plugins.scandroid.feature)
    alias(libs.plugins.scandroid.detekt)
}

android {
    namespace = "com.ikurek.scandroid.features.createscan"
}

dependencies {
    api(projects.features.createscan.usecase)
    implementation(libs.gms.playServices.mlkit.documentScanner)
    implementation(libs.kotlinx.coroutines.playServices)
}