plugins {
    id("weather.android.library")
    id("weather.compose")
}

android {
    namespace = "com.djekgrif.weather.core.presentation"
}

dependencies {
    implementation(projects.core.domain)
}