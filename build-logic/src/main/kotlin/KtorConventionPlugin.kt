import com.djekgrif.weather.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Ktor client + content negotiation. Applies the serialization convention plugin too,
 * since Ktor is wired with KotlinX Serialization JSON.
 */
class KtorConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("weather.serialization")
        dependencies {
            add("implementation", libs.findLibrary("ktor-client-core").get())
            add("implementation", libs.findLibrary("ktor-client-okhttp").get())
            add("implementation", libs.findLibrary("ktor-client-content-negotiation").get())
            add("implementation", libs.findLibrary("ktor-serialization-kotlinx-json").get())
            add("implementation", libs.findLibrary("ktor-client-logging").get())
        }
    }
}