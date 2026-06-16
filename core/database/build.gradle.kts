plugins {
    id("weather.android.library")
}

android {
    namespace = "com.djekgrif.weather.core.database"
}

dependencies {
    implementation(projects.core.domain)
}