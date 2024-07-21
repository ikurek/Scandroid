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
    implementation(projects.analytics)
    implementation(projects.features.createscan)
    implementation(projects.features.createscan.usecase)
    implementation(projects.features.home)
    implementation(projects.features.savedscans)
    implementation(projects.features.scandetails)
    implementation(projects.features.settings)
}
