package com.example.weathertest.data.network

import okhttp3.Interceptor

/**
 * This allows us to use dependency inject
 */
interface ConnectivityInterceptor : Interceptor
{
}