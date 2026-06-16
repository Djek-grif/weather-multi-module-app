plugins {
    id("weather.android.feature")
}

android {
    namespace = "com.djekgrif.weather.feature.home.presentation"
}

dependencies {
    implementation(projects.feature.home.domain)
    implementation(projects.core.domain)
    implementation(projects.core.presentation)
    implementation(projects.core.designSystem)
}