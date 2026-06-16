package com.djekgrif.weather.feature.home.domain.usecase

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import com.djekgrif.weather.core.domain.util.Result
import com.djekgrif.weather.feature.home.domain.FakeWeatherRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class WeatherUseCasesTest {

    @Test
    fun `GetCurrentWeather emits the repository's current weather stream`() = runTest {
        val repository = FakeWeatherRepository().apply {
            currentWeatherResult = Result.Success(FakeWeatherRepository.sampleCurrentWeather)
        }
        val useCase = GetCurrentWeatherUseCase(repository)

        useCase("London").test {
            assertThat(awaitItem())
                .isEqualTo(Result.Success(FakeWeatherRepository.sampleCurrentWeather))
            awaitComplete()
        }
    }

    @Test
    fun `SaveSelectedCity persists the city and GetSelectedCity emits it`() = runTest {
        val repository = FakeWeatherRepository()
        val saveSelectedCity = SaveSelectedCityUseCase(repository)
        val getSelectedCity = GetSelectedCityUseCase(repository)

        getSelectedCity().test {
            assertThat(awaitItem()).isEqualTo(null)

            saveSelectedCity("Paris")

            assertThat(awaitItem()).isEqualTo("Paris")
            assertThat(repository.savedCities).containsExactly("Paris")
        }
    }
}