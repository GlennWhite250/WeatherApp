package com.example.weathertest.data.db.unitlocalized.current

import androidx.room.ColumnInfo

/**
 * This implements the database from the interface UnitSpecificCurrentWeatherEntry
 * This is for the rest of the world
 */

data class MetricCurrentWeatherEntry(
    @ColumnInfo(name = "tempC")
    override val temperature: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String,
    @ColumnInfo(name = "windKph")
    override val windSpeed: Double,
    @ColumnInfo(name = "windDir")
    override val windDirection: String,
    @ColumnInfo(name = "precipMm")
    override val precipitationVolume: Double,
    @ColumnInfo(name = "feelslikeC")
    override val feelsLikeTemperature: Double,
    @ColumnInfo(name = "visKm")
    override val visibilityDistance: Double,
    @ColumnInfo(name = "uv")
    override val uv: Double,
    @ColumnInfo(name = "humidity")
    override val humidity: Int
    ): UnitSpecificCurrentWeatherEntry