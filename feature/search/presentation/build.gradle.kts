plugins {
    id("weather.android.feature")
}

android {
    namespace = "com.djekgrif.weather.feature.search.presentation"
}

dependencies {
    implementation(projects.feature.search.domain)
    implementation(projects.core.domain)
    implementation(projects.core.presentation)
    implementation(projects.core.designSystem)
}