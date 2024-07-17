plugins {
    alias(libs.plugins.scandroid.application)
    alias(libs.plugins.scandroid.application.compose)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.performance)
    alias(libs.plugins.gms.googleServices)
    alias(libs.plugins.gms.ossLicenses)
}

android {
    namespace = "com.ikurek.scandroid"

    defaultConfig {
        applicationId = "com.ikurek.scandroid"
        versionCode = 2
        versionName = "1.1.0"
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }

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
    implementation(projects.analytics)
    implementation(projects.core.design)
    implementation(projects.core.translations)
    implementation(projects.features.home)
    implementation(projects.features.createscan)
}
