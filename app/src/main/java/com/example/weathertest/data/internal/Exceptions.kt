package com.example.weathertest.data.internal

import java.io.IOException

class NoConnectivityException: IOException()
class LocationPermissionNotGrantedException : Exception()
class DateNotFoundException: Exception()