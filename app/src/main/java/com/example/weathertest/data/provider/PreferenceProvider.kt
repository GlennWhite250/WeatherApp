package com.example.weathertest.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.weathertest.data.internal.UnitSystem

abstract class PreferenceProvider(context: Context)
{
    //Creates an application Context
    private val appContext = context.applicationContext

    protected val preferences: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(appContext)
}