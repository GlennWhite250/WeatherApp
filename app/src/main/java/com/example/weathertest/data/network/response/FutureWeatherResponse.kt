package com.example.weathertest.data.network.response


import com.example.weathertest.data.db.entity.WeatherLocation
import com.google.gson.annotations.SerializedName

data class FutureWeatherResponse(
    @SerializedName("forecastday")
    val futureWeatherEntries: ForecastDaysContainer,
    val location: WeatherLocation
)