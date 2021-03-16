package com.example.weathertest.data.db

import android.content.Context
import androidx.room.*
import com.example.weathertest.data.db.entity.CurrentWeatherEntry
import com.example.weathertest.data.db.entity.FutureWeatherEntry
import com.example.weathertest.data.db.entity.WeatherLocation
import kotlinx.coroutines.internal.synchronized

@Database(entities = [CurrentWeatherEntry::class, WeatherLocation::class, FutureWeatherEntry::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class ForecastDatabase : RoomDatabase()
{
    /**
     * This will return an instance of the Dao that was created
     */
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun futureWeatherDao(): FutureWeatherDao
    abstract fun weatherLocationDao(): WeatherLocationDao

    /**
     * this is making the database a Singleton
     */
    companion object
    {
        @Volatile private var instance: ForecastDatabase? = null
        //This is for the threads to insure that no two things are doing anything at the sametime
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: kotlin.synchronized(LOCK)
        {
            instance ?: buildDatabase(context).also{ instance = it}
        }


        private fun buildDatabase(context: Context) = Room.databaseBuilder(context.applicationContext,
            ForecastDatabase::class.java,
            "forecast.db").build()
    }
}