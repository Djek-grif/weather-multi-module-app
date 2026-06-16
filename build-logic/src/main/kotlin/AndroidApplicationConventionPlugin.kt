import com.android.build.api.dsl.ApplicationExtension
import com.djekgrif.weather.buildlogic.ProjectConfig
import com.djekgrif.weather.buildlogic.configureKotlinJvmTarget
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        // AGP 9 has built-in Kotlin support, so the Kotlin Android plugin is applied implicitly.
        pluginManager.apply("com.android.application")
        extensions.configure<ApplicationExtension> {
            compileSdk = ProjectConfig.COMPILE_SDK
            defaultConfig {
                applicationId = "com.djekgrif.weather"
                minSdk = ProjectConfig.MIN_SDK
                targetSdk = ProjectConfig.TARGET_SDK
                versionCode = 1
                versionName = "1.0"
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }
            compileOptions {
                sourceCompatibility = ProjectConfig.JAVA_VERSION
                targetCompatibility = ProjectConfig.JAVA_VERSION
            }
        }
        configureKotlinJvmTarget()
    }
}