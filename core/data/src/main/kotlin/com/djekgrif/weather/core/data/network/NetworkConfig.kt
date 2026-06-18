package com.djekgrif.weather.core.data.network

/**
 * Network configuration injected into [HttpClientFactory]. Keeps secrets (the API key) and the
 * base URL out of the client/helper code — they are provided once from DI.
 */
data class NetworkConfig(
    val baseUrl: String,
    val apiKey: String,
    val units: String = "metric",
)