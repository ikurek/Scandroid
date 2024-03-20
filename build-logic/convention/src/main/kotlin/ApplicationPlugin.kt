import com.android.build.api.dsl.ApplicationExtension
import com.ikurek.scandroid.buildlogic.common.configureKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class ApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("com.ikurek.scandroid.hilt")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlin(this)
                defaultConfig.minSdk = SdkVersions.MIN
                defaultConfig.targetSdk = SdkVersions.TARGET
            }
        }
    }
}
