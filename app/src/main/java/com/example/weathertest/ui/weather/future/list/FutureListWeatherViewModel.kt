package com.example.weathertest.ui.weather.future.list

import androidx.lifecycle.ViewModel
import com.example.weathertest.data.internal.lazyDefered
import com.example.weathertest.data.provider.UnitProvider
import com.example.weathertest.data.repository.ForecastRepository
import com.example.weathertest.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureListWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val weatherEntries by lazyDefered {
        forecastRepository.getFutureWeatherList(LocalDate.now(), super.isMetricUnit)
    }
}