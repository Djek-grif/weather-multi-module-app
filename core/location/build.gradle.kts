plugins {
    id("weather.android.library")
    id("weather.koin")
}

android {
    namespace = "com.djekgrif.weather.core.location"
}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.gms.play.services.location)
    implementation(libs.kotlinx.coroutines.play.services)
}