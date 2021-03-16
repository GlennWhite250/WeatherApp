package com.example.weathertest.data.provider

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.example.weathertest.data.db.entity.WeatherLocation
import com.example.weathertest.data.internal.LocationPermissionNotGrantedException
import com.example.weathertest.data.internal.asDeferred
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Deferred

/**
 * These are constant values to represent out two location settings options
 */
const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"
const val CUSTOM_LOCATION = "CUSTOM_LOCATION"

class LocationProviderImpl(private val fusedLocationProviderClient: FusedLocationProviderClient,
                           context: Context) : PreferenceProvider(context), LocationProvider
{
    //defining the appContext
    private val appContext = context.applicationContext

    override suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation): Boolean
    {
        //This is a value that will determine if the location has changed
        val deviceLocationChanged = try
        {
            hasDeviceLocationChanged(lastWeatherLocation)
        }
        catch (e: LocationPermissionNotGrantedException)
        {
            false
        }

        return deviceLocationChanged || hasCustomLocationChanged(lastWeatherLocation)
    }

    override suspend fun getPreferredLocationString(): String
    {
        if(isUsingDeviceLocation())
        {
            try {
                val deviceLocation = getLastDeviceLocation().await() ?: return "${getCustomLocationName()}"
                return "${deviceLocation.latitude}, ${deviceLocation.longitude}"
            }
            catch (e: LocationPermissionNotGrantedException)
            {
                return "${getCustomLocationName()}"
            }
        }
        else
        {
            return "${getCustomLocationName()}"
        }
    }

    /**
     * This funtion helps us to determine if the use has changed the custom location
     */
    private suspend fun hasDeviceLocationChanged(lastWeatherLocation: WeatherLocation) : Boolean
    {
        //This if statement determines if the user is using the phones location for the data
        if(!isUsingDeviceLocation())
        {
            return false
        }

        //This is getting the location of the device, if there is no location previous stored then a false is returned
        val deviceLocation = getLastDeviceLocation().await() ?: return false


        //Comparing doubles cannot be done with the "=="
        val comparisonThreshold = 0.03
        return Math.abs(deviceLocation.latitude - lastWeatherLocation.lat) > comparisonThreshold &&
                Math.abs(deviceLocation.longitude - lastWeatherLocation.lon) > comparisonThreshold
    }

    /**
     * This helper function is simply calling the preference from the preference provider
     */
    private fun isUsingDeviceLocation() : Boolean
    {
        return preferences.getBoolean(USE_DEVICE_LOCATION, true)
    }


    //This Suppress is here becasue Kotlin does not see the hasLocationPermission function
    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocation(): Deferred<Location?>
    {
        return if(hasLocationPermission())
        {
            fusedLocationProviderClient.lastLocation.asDeferred()
        }
        else
        {
            throw LocationPermissionNotGrantedException()
        }
    }

    /**
     * The helper function is checking to see if the user has given the app permision to use it's location
     */
    private fun hasLocationPermission(): Boolean
    {
        return ContextCompat.checkSelfPermission(appContext,
        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun hasCustomLocationChanged(lastWeatherLocation: WeatherLocation) :Boolean
    {
        if(!isUsingDeviceLocation())
        {
            val customLocationName = getCustomLocationName()
            return customLocationName != lastWeatherLocation.name
        }
        //This is returning false if we are using the device location
        return false
    }

    private fun getCustomLocationName() : String?
    {
        return preferences.getString(CUSTOM_LOCATION, null)
    }
}