import com.djekgrif.weather.buildlogic.ProjectConfig
import com.djekgrif.weather.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

/**
 * Pure Kotlin/JVM module for the domain layer. No Android dependencies.
 */
class JvmDomainConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.jvm")
            apply("java-library")
        }
        extensions.configure<JavaPluginExtension> {
            sourceCompatibility = ProjectConfig.JAVA_VERSION
            targetCompatibility = ProjectConfig.JAVA_VERSION
        }
        extensions.configure<KotlinJvmProjectExtension> {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_11)
            }
        }
        dependencies {
            // api: Flow / CoroutineDispatcher appear in domain public signatures.
            add("api", libs.findLibrary("kotlinx-coroutines-core").get())

            add("testImplementation", libs.findLibrary("junit-jupiter").get())
            add("testImplementation", libs.findLibrary("turbine").get())
            add("testImplementation", libs.findLibrary("assertk").get())
            add("testImplementation", libs.findLibrary("kotlinx-coroutines-test").get())
            add("testRuntimeOnly", libs.findLibrary("junit-platform-launcher").get())
        }

        tasks.withType(Test::class.java).configureEach { useJUnitPlatform() }
    }
}