plugins {
    id("weather.android.library")
    id("weather.ktor")
    id("weather.koin")
}

android {
    namespace = "com.djekgrif.weather.feature.search.data"
}

dependencies {
    implementation(projects.feature.search.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}