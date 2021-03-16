package com.example.weathertest.data.db.unitlocalized.current

import androidx.room.ColumnInfo

/**
 * This implements the database from the interface UnitSpecificCurrentWeatherEntry
 * This is for the US
 */
data class ImperialCurrentWeatherEntry(
    @ColumnInfo(name = "tempF")
    override val temperature: Double,
    @ColumnInfo(name = "condition_text")
    override val conditionText: String,
    @ColumnInfo(name = "condition_icon")
    override val conditionIconUrl: String,
    @ColumnInfo(name = "windMph")
    override val windSpeed: Double,
    @ColumnInfo(name = "windDir")
    override val windDirection: String,
    @ColumnInfo(name = "precipIn")
    override val precipitationVolume: Double,
    @ColumnInfo(name = "feelslikeF")
    override val feelsLikeTemperature: Double,
    @ColumnInfo(name = "visMiles")
    override val visibilityDistance: Double,
    @ColumnInfo(name = "uv")
    override val uv: Double,
    @ColumnInfo(name = "humidity")
    override val humidity: Int
    ) : UnitSpecificCurrentWeatherEntry