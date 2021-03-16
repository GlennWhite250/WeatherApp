package com.example.weathertest.data.provider

import com.example.weathertest.data.db.entity.WeatherLocation

interface LocationProvider
{
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    suspend fun getPreferredLocationString(): String
}