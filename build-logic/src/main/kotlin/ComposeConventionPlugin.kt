import com.djekgrif.weather.buildlogic.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Enables Compose on an Android module. Apply AFTER an Android plugin
 * (weather.android.library or weather.android.application).
 */
class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        configureAndroidCompose()
    }
}