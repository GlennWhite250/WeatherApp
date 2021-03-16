package com.example.weathertest.ui.base

import androidx.lifecycle.ViewModel
import com.example.weathertest.data.internal.UnitSystem
import com.example.weathertest.data.internal.lazyDefered
import com.example.weathertest.data.provider.UnitProvider
import com.example.weathertest.data.repository.ForecastRepository

abstract class WeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
): ViewModel()
{
    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit: Boolean
    get() = unitSystem == UnitSystem.METRIC

    val weatherLocation by lazyDefered {
        forecastRepository.getWeatherLocation()
    }
}