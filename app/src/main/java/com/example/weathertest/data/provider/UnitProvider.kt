package com.example.weathertest.data.provider

import com.example.weathertest.data.internal.UnitSystem

/**
 * This allows us to get the system of the device running
 */
interface  UnitProvider
{
    fun getUnitSystem():UnitSystem
}