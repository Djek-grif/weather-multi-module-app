plugins {
    id("weather.android.library")
    id("weather.ktor")
}

android {
    namespace = "com.djekgrif.weather.core.data"
}

dependencies {
    implementation(projects.core.domain)
}