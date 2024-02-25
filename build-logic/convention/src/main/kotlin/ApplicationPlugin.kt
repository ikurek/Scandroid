import com.android.build.api.dsl.ApplicationExtension
import com.ikurek.scandroid.buildlogic.common.configureKotlin
import com.ikurek.scandroid.buildlogic.common.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("com.ikurek.scandroid.detekt")
                apply("com.ikurek.scandroid.hilt")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlin(this)
                defaultConfig.minSdk = 26
                defaultConfig.targetSdk = 34
            }
        }
    }
}