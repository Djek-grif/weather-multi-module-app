plugins {
    id("weather.android.library")
    id("weather.compose")
}

android {
    namespace = "com.djekgrif.weather.core.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
}