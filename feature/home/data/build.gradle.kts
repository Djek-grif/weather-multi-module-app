plugins {
    id("weather.android.library")
    id("weather.ktor")
}

android {
    namespace = "com.djekgrif.weather.feature.home.data"
}

dependencies {
    implementation(projects.feature.home.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.database)
}