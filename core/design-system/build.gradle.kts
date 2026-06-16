plugins {
    id("weather.android.library")
    id("weather.compose")
}

android {
    namespace = "com.djekgrif.weather.core.designsystem"
}

dependencies {
    implementation(projects.core.domain)
}