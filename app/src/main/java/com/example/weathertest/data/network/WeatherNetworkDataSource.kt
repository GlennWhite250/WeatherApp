package com.example.weathertest.data.network

import androidx.lifecycle.LiveData
import com.example.weathertest.data.network.response.CurrentWeatherResponse
import com.example.weathertest.data.network.response.FutureWeatherResponse

interface WeatherNetworkDataSource
{
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    val downloadedFutureWeather: LiveData<FutureWeatherResponse>

    suspend fun fetchCurrentWeather(loaction: String)

    suspend fun fetchFutureWeather(location: String)
}