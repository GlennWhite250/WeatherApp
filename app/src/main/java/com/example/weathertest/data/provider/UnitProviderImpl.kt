package com.example.weathertest.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.weathertest.data.internal.UnitSystem

//A global variable for the devices settings
const val UNIT_SYSTEM = "UNIT_SYSTEM"

class UnitProviderImpl(context: Context) : PreferenceProvider(context), UnitProvider
{

    /**
     * This is creating a variable and setting the devices "settings" to the app
     */
    override fun getUnitSystem(): UnitSystem
    {
        val selectedName = preferences.getString(UNIT_SYSTEM, UnitSystem.METRIC.name)
        return UnitSystem.valueOf(selectedName!!)
    }
}