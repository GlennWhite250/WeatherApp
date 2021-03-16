package com.example.weathertest.data.network.response

import com.example.weathertest.data.db.entity.FutureWeatherEntry
import com.google.gson.annotations.SerializedName


data class ForecastDaysContainer(
    @SerializedName("forecastday")
    val entries: List<FutureWeatherEntry>
)