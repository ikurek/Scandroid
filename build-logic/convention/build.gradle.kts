import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.ikurek.scandroid.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    implementation(libs.detekt.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("application") {
            id = "com.ikurek.scandroid.application"
            implementationClass = "ApplicationPlugin"
        }
        register("applicationCompose") {
            id = "com.ikurek.scandroid.application.compose"
            implementationClass = "ApplicationComposePlugin"
        }
        register("detekt") {
            id = "com.ikurek.scandroid.detekt"
            implementationClass = "DetektPlugin"
        }
        register("hilt") {
            id = "com.ikurek.scandroid.hilt"
            implementationClass = "HiltPlugin"
        }
        register("library") {
            id = "com.ikurek.scandroid.library"
            implementationClass = "LibraryPlugin"
        }
        register("libraryCompose") {
            id = "com.ikurek.scandroid.library.compose"
            implementationClass = "LibraryComposePlugin"
        }
    }
}