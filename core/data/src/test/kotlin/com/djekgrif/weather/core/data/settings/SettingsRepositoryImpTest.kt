package com.djekgrif.weather.core.data.settings

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.djekgrif.weather.core.data.preferences.PreferencesDataSource
import com.djekgrif.weather.core.domain.settings.TemperatureUnit
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsRepositoryImpTest {

    private val preferences = FakePreferencesDataSource()
    private val repository = SettingsRepositoryImp(preferences)

    @Test
    fun `getTemperatureUnit defaults to Celsius when nothing stored`() = runTest {
        repository.getTemperatureUnit().test {
            assertThat(awaitItem()).isEqualTo(TemperatureUnit.CELSIUS)
        }
    }

    @Test
    fun `setTemperatureUnit persists and is read back`() = runTest {
        repository.getTemperatureUnit().test {
            assertThat(awaitItem()).isEqualTo(TemperatureUnit.CELSIUS)
            repository.setTemperatureUnit(TemperatureUnit.FAHRENHEIT)
            assertThat(awaitItem()).isEqualTo(TemperatureUnit.FAHRENHEIT)
        }
    }

    @Test
    fun `getTemperatureUnit falls back to Celsius for an unrecognised stored value`() = runTest {
        preferences.putString(PreferencesDataSource.TEMPERATURE_UNIT_KEY, "KELVIN")

        repository.getTemperatureUnit().test {
            assertThat(awaitItem()).isEqualTo(TemperatureUnit.CELSIUS)
        }
    }

    private class FakePreferencesDataSource : PreferencesDataSource {
        private val strings = mutableMapOf<String, MutableStateFlow<String?>>()
        private fun flowFor(key: String) = strings.getOrPut(key) { MutableStateFlow(null) }

        override fun getString(key: String, defaultValue: String?): Flow<String?> =
            flowFor(key).map { it ?: defaultValue }

        override suspend fun putString(key: String, value: String) {
            flowFor(key).value = value
        }

        override fun getInt(key: String, defaultValue: Int) = flowOf(defaultValue)
        override suspend fun putInt(key: String, value: Int) = Unit
        override fun getLong(key: String, defaultValue: Long) = flowOf(defaultValue)
        override suspend fun putLong(key: String, value: Long) = Unit
        override fun getBoolean(key: String, defaultValue: Boolean) = flowOf(defaultValue)
        override suspend fun putBoolean(key: String, value: Boolean) = Unit
        override fun getDouble(key: String, defaultValue: Double) = flowOf(defaultValue)
        override suspend fun putDouble(key: String, value: Double) = Unit
        override suspend fun remove(key: String) { strings.remove(key) }
        override suspend fun clear() { strings.clear() }
    }
}
