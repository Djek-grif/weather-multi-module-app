package com.djekgrif.weather.feature.search.presentation.mapper

import com.djekgrif.weather.feature.search.domain.model.City
import com.djekgrif.weather.feature.search.presentation.model.CityUi

fun City.toUi(): CityUi = CityUi(
    id = "${name}|${country}|${state.orEmpty()}|${latitude}|${longitude}",
    name = name,
    region = listOfNotNull(state?.takeIf { it.isNotBlank() }, country.takeIf { it.isNotBlank() })
        .joinToString(", "),
)