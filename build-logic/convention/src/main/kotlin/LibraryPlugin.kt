import com.android.build.gradle.LibraryExtension
import com.ikurek.scandroid.buildlogic.common.configureKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class LibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("com.ikurek.scandroid.detekt")
            }

            extensions.configure<LibraryExtension> {
                configureKotlin(this)
                defaultConfig.minSdk = 26
                defaultConfig.targetSdk = 34
            }
        }
    }
}