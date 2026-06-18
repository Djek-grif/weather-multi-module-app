import com.android.build.api.dsl.LibraryExtension
import com.djekgrif.weather.buildlogic.ProjectConfig
import com.djekgrif.weather.buildlogic.configureKotlinJvmTarget
import com.djekgrif.weather.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        // AGP 9 has built-in Kotlin support, so the Kotlin Android plugin is applied implicitly.
        pluginManager.apply("com.android.library")
        extensions.configure<LibraryExtension> {
            compileSdk = ProjectConfig.COMPILE_SDK
            defaultConfig {
                minSdk = ProjectConfig.MIN_SDK
            }
            compileOptions {
                sourceCompatibility = ProjectConfig.JAVA_VERSION
                targetCompatibility = ProjectConfig.JAVA_VERSION
            }
        }
        configureKotlinJvmTarget()

        dependencies {
            add("testImplementation", libs.findLibrary("junit-jupiter").get())
            add("testImplementation", libs.findLibrary("turbine").get())
            add("testImplementation", libs.findLibrary("assertk").get())
            add("testImplementation", libs.findLibrary("kotlinx-coroutines-test").get())
            add("testRuntimeOnly", libs.findLibrary("junit-platform-launcher").get())
        }
        tasks.withType(Test::class.java).configureEach { useJUnitPlatform() }
    }
}