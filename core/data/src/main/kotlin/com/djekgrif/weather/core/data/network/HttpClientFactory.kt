package com.djekgrif.weather.core.data.network

import com.djekgrif.weather.core.data.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Builds the shared Ktor [HttpClient]. The engine is injected so tests can supply a MockEngine.
 * The OpenWeatherMap API key (`appid`) and metric units are appended to every request by default.
 */
object HttpClientFactory {
    fun create(engine: HttpClientEngine): HttpClient = HttpClient(engine) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                },
            )
        }
        install(Logging) {
            level = LogLevel.INFO
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
            url {
                parameters.append("appid", BuildConfig.API_KEY)
                parameters.append("units", "metric")
            }
        }
    }
}