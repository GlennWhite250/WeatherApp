package com.example.weathertest.ui.weather.current

import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.example.weathertest.data.internal.UnitSystem
import com.example.weathertest.data.internal.lazyDefered
import com.example.weathertest.data.provider.UnitProvider
import com.example.weathertest.data.repository.ForecastRepository
import com.example.weathertest.ui.base.WeatherViewModel
import kotlinx.android.synthetic.main.current_weather_fragment.view.*

class CurrentWeatherViewModel(private val forecastRepository: ForecastRepository,
                              unitProvider: UnitProvider) : WeatherViewModel(forecastRepository, unitProvider)
{
    val weather by lazyDefered {
        forecastRepository.getCurrentWeather(super.isMetricUnit)
    }
}