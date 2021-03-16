package com.example.weathertest.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathertest.data.provider.UnitProvider
import com.example.weathertest.data.repository.ForecastRepository

class CurrentWeatherViewModelFactory (private val forecastRepository: ForecastRepository,
private val unitProvider: UnitProvider): ViewModelProvider.NewInstanceFactory()
{
    //The cast as T in the return is uncheck, thus we use the @Supress
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return CurrentWeatherViewModel(forecastRepository, unitProvider) as T
    }
}