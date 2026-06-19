plugins {
    id("weather.android.feature")
}

android {
    namespace = "com.djekgrif.weather.feature.settings.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.designSystem)
}
