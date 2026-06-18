package com.djekgrif.weather.feature.home.data.mapper

import assertk.assertThat
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import com.djekgrif.weather.feature.home.data.remote.dto.ForecastItemDto
import com.djekgrif.weather.feature.home.data.remote.dto.ForecastResponseDto
import com.djekgrif.weather.feature.home.data.remote.dto.MainDto
import com.djekgrif.weather.feature.home.data.remote.dto.SysDto
import com.djekgrif.weather.feature.home.data.remote.dto.WeatherDescriptionDto
import com.djekgrif.weather.feature.home.data.remote.dto.WeatherResponseDto
import com.djekgrif.weather.feature.home.data.remote.dto.WindDto
import java.time.Instant
import org.junit.jupiter.api.Test

class WeatherDtoMappersTest {

    @Test
    fun `toCurrentWeather maps response fields and first condition`() {
        val dto = WeatherResponseDto(
            weather = listOf(WeatherDescriptionDto(description = "clear sky", icon = "01d")),
            main = MainDto(temp = 20.0, feelsLike = 19.0, pressure = 1012, humidity = 50),
            wind = WindDto(speed = 3.0),
            sys = SysDto(sunrise = 1000, sunset = 2000),
            name = "London",
        )

        val weather = dto.toCurrentWeather()

        assertThat(weather.cityName).isEqualTo("London")
        assertThat(weather.temperature).isEqualTo(20.0)
        assertThat(weather.feelsLike).isEqualTo(19.0)
        assertThat(weather.description).isEqualTo("clear sky")
        assertThat(weather.iconCode).isEqualTo("01d")
        assertThat(weather.humidity).isEqualTo(50)
        assertThat(weather.windSpeed).isEqualTo(3.0)
        assertThat(weather.pressure).isEqualTo(1012)
        assertThat(weather.sunrise).isEqualTo(1000)
        assertThat(weather.sunset).isEqualTo(2000)
    }

    @Test
    fun `toDailyForecasts groups 3-hour slots into one entry per day with min and max temps`() {
        fun item(time: String, min: Double, max: Double, icon: String) = ForecastItemDto(
            dt = Instant.parse(time).epochSecond,
            main = MainDto(tempMin = min, tempMax = max),
            weather = listOf(WeatherDescriptionDto(description = "d-$icon", icon = icon)),
            wind = WindDto(speed = 2.0),
        )

        val dto = ForecastResponseDto(
            list = listOf(
                item("2024-01-01T09:00:00Z", min = 5.0, max = 10.0, icon = "01d"),
                item("2024-01-01T12:00:00Z", min = 6.0, max = 14.0, icon = "02d"),
                item("2024-01-01T18:00:00Z", min = 4.0, max = 11.0, icon = "03d"),
                item("2024-01-02T12:00:00Z", min = 1.0, max = 7.0, icon = "10d"),
            ),
        )

        val daily = dto.toDailyForecasts()

        assertThat(daily).hasSize(2)
        val day1 = daily[0]
        assertThat(day1.tempMin).isEqualTo(4.0)
        assertThat(day1.tempMax).isEqualTo(14.0)
        // 12:00 slot is closest to midday → represents the day.
        assertThat(day1.iconCode).isEqualTo("02d")
        val day2 = daily[1]
        assertThat(day2.tempMin).isEqualTo(1.0)
        assertThat(day2.tempMax).isEqualTo(7.0)
        assertThat(day2.iconCode).isEqualTo("10d")
    }
}