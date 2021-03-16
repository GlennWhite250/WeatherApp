package com.example.weathertest.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weathertest.data.OpenWeatherApiService
import com.example.weathertest.data.internal.NoConnectivityException
import com.example.weathertest.data.network.response.CurrentWeatherResponse
import com.example.weathertest.data.network.response.FutureWeatherResponse

//This is a constant for the number of days into the future we are looking
const val FORECAST_DAYS_COUNT = 7

class WeatherNetworkDataSourceImpl(private val openWeatherApiService: OpenWeatherApiService) : WeatherNetworkDataSource
{
    //This allows us to change the downloaded weather
    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(loaction: String)
    {
        try
        {
            val fetchedCurrentWeather = openWeatherApiService
                .getCurrentWeather(location = "Atlanta")
                .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }
        catch (e: NoConnectivityException)
        {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }

    private val _downloadedFutureWeather = MutableLiveData<FutureWeatherResponse>()

    override val downloadedFutureWeather: LiveData<FutureWeatherResponse>
        get() = _downloadedFutureWeather

    override suspend fun fetchFutureWeather(location: String) {
        try
        {
            val fetchedFutureWeather = openWeatherApiService
                .getFutureWeather(location, FORECAST_DAYS_COUNT)
                .await()
            _downloadedFutureWeather.postValue(fetchedFutureWeather)
        }
        catch (e: NoConnectivityException)
        {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }
}