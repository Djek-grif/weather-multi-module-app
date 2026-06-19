package com.djekgrif.weather.feature.search.data.mapper

import com.djekgrif.weather.feature.search.data.remote.dto.CityDto
import com.djekgrif.weather.feature.search.domain.model.City

fun CityDto.toCity(): City = City(
    name = name,
    country = country,
    state = state,
    latitude = latitude,
    longitude = longitude,
)