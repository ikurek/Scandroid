plugins {
    alias(libs.plugins.scandroid.library)
    alias(libs.plugins.scandroid.library.compose)
}

android {
    namespace = "com.ikurek.scandroid.common.ui.pdfview"
}

dependencies {
    implementation(libs.coil.compose)
    implementation(libs.compose.zoomable)
    implementation(projects.core.design)
    implementation(projects.core.translations)
}
