plugins {
    id("weather.android.application")
    id("weather.compose")
    id("weather.koin")
}

android {
    namespace = "com.djekgrif.weather"

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.presentation)
    implementation(projects.core.designSystem)
    implementation(projects.feature.home.presentation)
    implementation(projects.feature.search.presentation)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}