package com.djekgrif.weather.feature.home.presentation.mapper

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.djekgrif.weather.core.domain.settings.TemperatureUnit
import com.djekgrif.weather.feature.home.domain.model.CurrentWeather
import org.junit.jupiter.api.Test

class WeatherUiMappersTest {

    @Test
    fun `celsius formats temperature and wind in metric`() {
        val ui = sample.toUi(highTemperature = null, lowTemperature = null, unit = TemperatureUnit.CELSIUS)

        assertThat(ui.temperature).isEqualTo("20°C")
        assertThat(ui.feelsLike).isEqualTo("18°C")
        assertThat(ui.windSpeed).isEqualTo("10 m/s")
    }

    @Test
    fun `fahrenheit converts temperature and wind to imperial`() {
        val ui = sample.toUi(highTemperature = null, lowTemperature = null, unit = TemperatureUnit.FAHRENHEIT)

        // 20°C -> 68°F, 18°C -> 64°F (rounded)
        assertThat(ui.temperature).isEqualTo("68°F")
        assertThat(ui.feelsLike).isEqualTo("64°F")
        // 10 m/s -> 22 mph (10 * 2.23694 = 22.37, rounded)
        assertThat(ui.windSpeed).isEqualTo("22 mph")
    }

    private companion object {
        val sample = CurrentWeather(
            cityName = "London",
            temperature = 20.0,
            feelsLike = 18.0,
            description = "clear sky",
            iconCode = "01d",
            humidity = 50,
            windSpeed = 10.0,
            pressure = 1012,
            sunrise = 1000,
            sunset = 2000,
            timezoneOffsetSeconds = 0,
            lastUpdated = null,
        )
    }
}
