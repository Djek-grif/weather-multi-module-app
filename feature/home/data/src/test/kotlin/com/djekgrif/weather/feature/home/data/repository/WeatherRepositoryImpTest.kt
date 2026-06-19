package com.djekgrif.weather.feature.home.data.repository

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.djekgrif.weather.core.domain.util.DataError
import com.djekgrif.weather.core.domain.util.Result
import com.djekgrif.weather.feature.home.domain.model.CurrentWeather
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryImpTest {

    private lateinit var remote: FakeWeatherRemoteDataSource
    private lateinit var local: FakeWeatherLocalDataSource
    private lateinit var preferences: FakePreferencesDataSource
    private lateinit var repository: WeatherRepositoryImp

    @BeforeEach
    fun setUp() {
        remote = FakeWeatherRemoteDataSource()
        local = FakeWeatherLocalDataSource()
        preferences = FakePreferencesDataSource()
        repository = WeatherRepositoryImp(
            remoteDataSource = remote,
            localDataSource = local,
            preferencesDataSource = preferences,
            dispatchers = TestDispatcherProvider(UnconfinedTestDispatcher()),
            now = { FIXED_NOW },
        )
    }

    @Test
    fun `getCurrentWeather with no cache emits fresh result stamped with now and caches it`() = runTest {
        remote.currentWeatherResult = Result.Success(london)

        repository.getCurrentWeather("London").test {
            assertThat(awaitItem()).isEqualTo(Result.Success(london.copy(lastUpdated = FIXED_NOW)))
            awaitComplete()
        }
        assertThat(local.getCurrentWeather("London")).isEqualTo(london)
    }

    @Test
    fun `getCurrentWeather emits cached value first then refreshed value`() = runTest {
        local.upsertCurrentWeather(cachedLondon, cachedAt = 0)
        remote.currentWeatherResult = Result.Success(london)

        repository.getCurrentWeather("London").test {
            assertThat(awaitItem()).isEqualTo(Result.Success(cachedLondon))
            assertThat(awaitItem()).isEqualTo(Result.Success(london.copy(lastUpdated = FIXED_NOW)))
            awaitComplete()
        }
    }

    @Test
    fun `getCurrentWeather keeps cached value when remote fails`() = runTest {
        local.upsertCurrentWeather(cachedLondon, cachedAt = 0)
        remote.currentWeatherResult = Result.Error(DataError.Network.NO_INTERNET)

        repository.getCurrentWeather("London").test {
            assertThat(awaitItem()).isEqualTo(Result.Success(cachedLondon))
            awaitComplete()
        }
    }

    @Test
    fun `getCurrentWeather emits Error when remote fails and nothing is cached`() = runTest {
        remote.currentWeatherResult = Result.Error(DataError.Network.NO_INTERNET)

        repository.getCurrentWeather("London").test {
            assertThat(awaitItem()).isEqualTo(Result.Error(DataError.Network.NO_INTERNET))
            awaitComplete()
        }
    }

    @Test
    fun `saveSelectedCity persists and getSelectedCity emits it`() = runTest {
        repository.getSelectedCity().test {
            assertThat(awaitItem()).isEqualTo(null)
            repository.saveSelectedCity("Paris")
            assertThat(awaitItem()).isEqualTo("Paris")
        }
    }

    private companion object {
        const val FIXED_NOW = 1_700_000_000_000L
        val london = CurrentWeather(
            cityName = "London",
            temperature = 20.0,
            feelsLike = 19.0,
            description = "clear sky",
            iconCode = "01d",
            humidity = 50,
            windSpeed = 3.0,
            pressure = 1012,
            sunrise = 1000,
            sunset = 2000,
            timezoneOffsetSeconds = 0,
        )
        val cachedLondon = london.copy(temperature = 12.0, description = "cached")
    }
}