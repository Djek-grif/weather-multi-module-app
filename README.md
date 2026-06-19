# Weather — Multi-Module Android App

A modern Android weather app showing current conditions and a multi-day forecast for a
searched or auto-detected city. Built with Jetpack Compose and a layered, multi-module
Clean Architecture.

## Features

- **Today** — current temperature, "feels like", condition, humidity / wind / pressure,
  and a sunrise→sunset arc rendered in the city's local time.
- **Week** — multi-day forecast aggregated from the 3-hour OpenWeatherMap feed.
- **City search** — debounced geocoding search; the selected city is persisted and shared
  across tabs.
- **Current location** — one-tap detect-my-city via runtime location permission, with
  graceful fallback to search when denied/unavailable.
- **Offline-first** — cached weather is shown immediately and kept on refresh failure, with a
  relative "Updated …" timestamp and an offline banner.
- **Settings** — °C / °F toggle, persisted in DataStore and applied app-wide (client-side
  conversion — no re-fetch).
- Pull-to-refresh, skeleton loading placeholders, error states with retry, edge-to-edge,
  and accessibility (content descriptions, live-region banner).

## Tech stack

| Concern | Choice |
|---|---|
| UI | Jetpack Compose + Material 3 |
| Async | Coroutines + Flow |
| DI | Koin |
| Networking | Ktor (OkHttp engine) |
| Serialization | KotlinX Serialization |
| Local storage | Room (cache) + DataStore (preferences) |
| Navigation | Type-safe Compose Navigation |
| Images | Coil |
| Build | Gradle version catalog + `:build-logic` convention plugins (`weather.*`) |
| Tests | JUnit5, Turbine, AssertK, coroutines-test |
| API | OpenWeatherMap (current, forecast, geocoding) |

Presentation is **MVVM + reactive streams**: a `ViewModel` exposes `StateFlow<UiState>`
assembled by `combine`/`flatMapLatest`/`stateIn`, with plain action methods and one-off
effects via a small `Channel`/`SharedFlow`. Android-only (no KMP common source set).

## Module structure

```
:app                       Entry point, Koin startup, NavHost, MainActivity

:core
  :domain                  Pure Kotlin: models, Result/DataError, use cases, settings, dispatchers
  :data                    Ktor client, DataStore preferences, connectivity, settings repo impl
  :database                Room database, DAOs, entities
  :location                FusedLocationProvider implementation
  :presentation            UiText, ObserveAsEvents, permission helpers, error→UiText mappers
  :design-system           Material 3 theme + shared components (WeatherIcon, ErrorState,
                           LoadingIndicator, OfflineBanner, skeletons, shimmer modifier)

:feature
  :home   { :domain, :data, :presentation }   Today + Week tabs, weather repo, location
  :search { :domain, :data, :presentation }   City geocoding search
  :settings { :presentation }                 °C/°F selector (logic shared from :core)
```

### Dependency rules

- Each feature is split into `:domain` (pure), `:data`, and `:presentation` layers.
- **Features never depend on each other** — they communicate only through navigation.
  App-wide concerns consumed by multiple features (e.g. the temperature-unit setting) live in
  `:core`, not in a feature.
- `:core:domain` is plain JVM and depends on nothing else in the project.

## Architecture flow

```
Compose Screen → ViewModel (StateFlow) → UseCase → Repository
                                                     ├── Remote (Ktor)  → DTO → domain
                                                     └── Local  (Room/DataStore) → entity → domain
```

The weather repository is offline-first: it emits cached data first, then fetches fresh data,
persists it, and emits it; on network failure it retains the cached value.

## Getting started

1. Get a free API key from [OpenWeatherMap](https://openweathermap.org/api).
2. Add it to `local.properties` (not committed):
   ```properties
   OPEN_WEATHER_API_KEY=your_key_here
   ```
3. Build & run:
   ```bash
   ./gradlew assembleDebug          # build
   ./gradlew test                   # unit tests
   ```

Requires a recent Android Studio (AGP 9 / Gradle 9.4).