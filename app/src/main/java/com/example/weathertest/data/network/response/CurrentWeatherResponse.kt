package com.example.weathertest.data.network.response

import com.example.weathertest.data.db.entity.CurrentWeatherEntry
import com.example.weathertest.data.db.entity.WeatherLocation
import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(
    /**
     * This is to ensure that the api links to this renamed field
     */
    @SerializedName("current")
    val currentWeatherEntry : CurrentWeatherEntry,
    val location: WeatherLocation
)