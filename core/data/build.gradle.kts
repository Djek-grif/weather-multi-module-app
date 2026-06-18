import java.util.Properties

plugins {
    id("weather.android.library")
    id("weather.ktor")
    id("weather.koin")
}

// API key is kept out of source control: set OPEN_WEATHER_API_KEY in local.properties.
val openWeatherApiKey: String = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) file.inputStream().use { load(it) }
}.getProperty("OPEN_WEATHER_API_KEY", "")

android {
    namespace = "com.djekgrif.weather.core.data"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"https://api.openweathermap.org\"")
        buildConfigField("String", "API_KEY", "\"$openWeatherApiKey\"")
    }
}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.androidx.datastore.preferences)
}