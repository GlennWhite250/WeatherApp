package com.example.weathertest.data.db.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//This is a constant for the primary key. This way we always know what the key is
const val CURRENT_WEAHTER_ID = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(
    //We use the embedded to allow the condiditon to appear in out database as the three seperate fields
    @Embedded(prefix = "condition_")
    val condition: Condition,
    @SerializedName("feelslike_c")
    val feelslikeC: Double,
    @SerializedName("feelslike_f")
    val feelslikeF: Double,
    @SerializedName("gust_kph")
    val gustKph: Double,
    @SerializedName("gust_mph")
    val gustMph: Double,
    @SerializedName("is_day")
    val isDay: Int,
    @SerializedName("precip_in")
    val precipIn: Double,
    @SerializedName("precip_mm")
    val precipMm: Double,
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("temp_f")
    val tempF: Double,
    val uv: Double,
    @SerializedName("vis_km")
    val visKm: Double,
    @SerializedName("vis_miles")
    val visMiles: Double,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_kph")
    val windKph: Double,
    @SerializedName("wind_mph")
    val windMph: Double,
    @SerializedName("wind_degree")
    val windDegree: Double,
    @SerializedName("pressure_in")
    val pressureIn: Double,
    @SerializedName("pressure_mb")
    val pressureMb: Double,
    val humidity: Int

    /**
     * These fields are not used.
     * @SerializedName("last_updated")
     * val lastUpdated: String,
     * @SerializedName("last_updated_epoch")
     * val lastUpdatedEpoch: Int,
     * val humidity: Int,
     * val cloud: Int,
     */

)

/**
 * This is creating the Primary key for our database with a constant primary key
 */
{
    //This prevents the system to creating unique primary keys automatically
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEAHTER_ID
}