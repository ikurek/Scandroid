plugins {
    alias(libs.plugins.scandroid.library)
    alias(libs.plugins.scandroid.library.compose)
}

android {
    namespace = "com.ikurek.scandroid.core.design"
}

dependencies {
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.material.extendedIcons)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.ui.tooling.preview)
    debugApi(libs.androidx.compose.ui.tooling)
    implementation(projects.core.translations)
}
