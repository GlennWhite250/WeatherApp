package com.example.weathertest.data.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

const val WEATHER_LOCATION_ID = 0

@Entity(tableName = "weather_location")
data class WeatherLocation(
    val country: String,
    val lat: Double,
    @SerializedName("localtime_epoch")
    val localtimeEpoch: Long,
    val lon: Double,
    val name: String,
    val region: String,
    @SerializedName("tz_id")
    val tzId: String
)
{
    @PrimaryKey(autoGenerate = false)
    var id: Int = WEATHER_LOCATION_ID

    /**
     * This helper function will get the time of the location with proper time zone
     */
    val zonedDateTime: ZonedDateTime
    get()
    {
        //get the local time epoch and store it as an instant
        val instant = Instant.ofEpochSecond(localtimeEpoch)

        //takes the time zone and parse it into the zone ID
        val zoneID = ZoneId.of(tzId)

        //Create the Zone Date Time that gets an instance of the two new variables
        return ZonedDateTime.ofInstant(instant, zoneID)
    }
}

