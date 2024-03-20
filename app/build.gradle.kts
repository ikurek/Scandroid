plugins {
    alias(libs.plugins.scandroid.application)
    alias(libs.plugins.scandroid.application.compose)
}

android {
    namespace = "com.ikurek.scandroid"

    defaultConfig {
        applicationId = "com.ikurek.scandroid"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(projects.core.design)
    implementation(projects.core.translations)
    implementation(projects.features.home)
    implementation(projects.features.createscan)
}