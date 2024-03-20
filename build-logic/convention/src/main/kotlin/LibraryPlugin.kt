import com.android.build.gradle.LibraryExtension
import com.ikurek.scandroid.buildlogic.common.configureKotlin
import com.ikurek.scandroid.buildlogic.common.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlin(this)
                defaultConfig.minSdk = SdkVersions.MIN
                defaultConfig.targetSdk = SdkVersions.TARGET
            }

            dependencies {
                add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
            }
        }
    }
}
