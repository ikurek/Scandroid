import com.android.build.gradle.LibraryExtension
import com.ikurek.scandroid.buildlogic.common.configureCompose
import com.ikurek.scandroid.buildlogic.common.configureKotlin
import com.ikurek.scandroid.buildlogic.common.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class FeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("com.ikurek.scandroid.hilt")
            }

            extensions.configure<LibraryExtension> {
                configureKotlin(this)
                configureCompose(this)
                defaultConfig.minSdk = SdkVersions.MIN
                defaultConfig.targetSdk = SdkVersions.TARGET
            }

            dependencies {
                add("implementation", project(":core:design"))
                add("implementation", project(":core:translations"))
                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
                add(
                    "implementation",
                    libs.findLibrary("androidx.lifecycle.viewmodel.compose").get()
                )
                add("implementation", libs.findLibrary("androidx.navigation.compose").get())
                add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
            }
        }
    }
}
