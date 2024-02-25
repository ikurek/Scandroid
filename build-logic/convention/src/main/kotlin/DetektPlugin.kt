import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class DetektPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        pluginManager.apply("io.gitlab.arturbosch.detekt")

        extensions.configure<DetektExtension> {
            autoCorrect = true
            buildUponDefaultConfig = true
            config.setFrom("$rootDir/detekt.yml")
        }

        dependencies {
            add("detektPlugins", libs.findLibrary("detekt.formatting").get())
            add("detektPlugins", libs.findLibrary("detekt.rules.compose").get())
        }
    }
}