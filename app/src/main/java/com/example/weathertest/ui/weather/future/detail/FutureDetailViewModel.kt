package com.example.weathertest.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import com.example.weathertest.data.internal.lazyDefered
import com.example.weathertest.data.provider.UnitProvider
import com.example.weathertest.data.repository.ForecastRepository
import com.example.weathertest.ui.base.WeatherViewModel
import org.threeten.bp.LocalDate

class FutureDetailViewModel(
    private val detailDate: LocalDate,
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    val weather by lazyDefered {
        forecastRepository.getFutureWeatherByDate(detailDate, super.isMetricUnit)
    }
}