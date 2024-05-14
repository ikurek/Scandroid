package com.ikurek.scandroid.buildlogic.common

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

        buildFeatures {
            compose = true
        }

        dependencies {
            add("implementation", platform(libs.findLibrary("androidx.compose.bom").get()))
            add("implementation", libs.findLibrary("androidx.compose.material3").get())
            add("implementation", libs.findLibrary("androidx.compose.ui").get())
            add("implementation", libs.findLibrary("androidx.compose.ui.tooling.preview").get())
            add("debugImplementation", libs.findLibrary("androidx.compose.ui.tooling").get())
        }
    }
}
