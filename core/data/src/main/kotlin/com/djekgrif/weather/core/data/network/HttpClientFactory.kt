package com.djekgrif.weather.core.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Builds the shared Ktor [HttpClient]. The engine and `config` are injected so tests can supply a
 * MockEngine and a fake config.
 *
 * The base URL and the OpenWeatherMap `appid`/`units` are applied as request DEFAULTS. Ktor only
 * merges these defaults when the per-request URL is RELATIVE (host empty) — see
 * [HttpClient.getResult], which builds path-only requests. Using an absolute per-request URL would
 * make Ktor skip the whole default URL block (and the `appid`), causing 401s.
 */
object HttpClientFactory {
    fun create(engine: HttpClientEngine, config: NetworkConfig): HttpClient = HttpClient(engine) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                },
            )
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = Logger.SIMPLE
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
            url {
                takeFrom(config.baseUrl)
                parameters.append("appid", config.apiKey)
                parameters.append("units", config.units)
            }
        }
    }
}