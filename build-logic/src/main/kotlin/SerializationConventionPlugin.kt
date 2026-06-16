import com.djekgrif.weather.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class SerializationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply(libs.findPlugin("kotlin-serialization").get().get().pluginId)
        dependencies {
            add("implementation", libs.findLibrary("kotlinx-serialization-json").get())
        }
    }
}