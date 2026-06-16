import com.djekgrif.weather.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Bundles everything a feature :presentation module needs:
 * Android library + Compose + Koin + serialization + ViewModel/Navigation deps.
 */
class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("weather.android.library")
            apply("weather.compose")
            apply("weather.koin")
            apply("weather.serialization")
        }
        dependencies {
            add("implementation", libs.findLibrary("androidx-lifecycle-viewmodel-compose").get())
            add("implementation", libs.findLibrary("androidx-navigation-compose").get())
            add("implementation", libs.findLibrary("koin-androidx-compose").get())
            add("implementation", libs.findLibrary("coil-compose").get())
        }
    }
}