package com.example.weathertest.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weathertest.data.db.entity.CURRENT_WEAHTER_ID
import com.example.weathertest.data.db.entity.CurrentWeatherEntry
import com.example.weathertest.data.db.unitlocalized.current.ImperialCurrentWeatherEntry
import com.example.weathertest.data.db.unitlocalized.current.MetricCurrentWeatherEntry

/**
 * This is creating the Dao for our database
 */
@Dao
interface CurrentWeatherDao
{
    /**
     * This function is used to put the data
     */
    //This line ensures that the entry in the database is always replaced
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    //This calls all the data from the database, there will be only one row/column at a time for metric
    @Query("select * from current_weather where id = $CURRENT_WEAHTER_ID")
    fun getWeatherMetric(): LiveData<MetricCurrentWeatherEntry>

    //This calls all the data from the database, there will be only one row/column at a time for imperial
    @Query("select * from current_weather where id = $CURRENT_WEAHTER_ID")
    fun getWeatherImperial(): LiveData<ImperialCurrentWeatherEntry>
}