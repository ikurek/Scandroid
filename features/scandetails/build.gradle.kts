plugins {
    alias(libs.plugins.scandroid.feature)
}

android {
    namespace = "com.ikurek.scandroid.features.scandetails"
}

dependencies {
    implementation(projects.common.ui.pdfview)
    implementation(projects.features.savedscans.usecase)
    implementation(projects.features.scandetails.usecase)
    implementation(libs.coil.compose)
    implementation(libs.compose.zoomable)
}
