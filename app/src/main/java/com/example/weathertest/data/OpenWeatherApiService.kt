package com.example.weathertest.data

import com.example.weathertest.data.network.ConnectivityInterceptor
import com.example.weathertest.data.network.response.CurrentWeatherResponse
import com.example.weathertest.data.network.response.FutureWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The constant variable to represent our api key
 */
const val API_KEY = "0c67ea5629f64b2d80d184247201208"

/**
 * This is the full path for the current qurery
 * https://api.weatherapi.com/v1/current.json?key=0c67ea5629f64b2d80d184247201208&q=Atlanta
 *
 * This is the full path for the future qurery
 * http://api.weatherapi.com/v1/forecast.json?key=0c67ea5629f64b2d80d184247201208&q=Los%20Angeles&days=1
 *
 */
interface OpenWeatherApiService
{
    /**
     * This is the function that calls the query from the web api
     */
    @GET("current.json")
    fun getCurrentWeather(@Query("q") location: String)
    : Deferred<CurrentWeatherResponse>

    /**
     * This is the function that calls the query from the web api
     */
    @GET("forecast.json")
    fun getFutureWeather(
        @Query("q") location: String,
        @Query("days") days: Int) : Deferred<FutureWeatherResponse>

    companion object
    {
        /**
         * This inserts the api key into each query
         */
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): OpenWeatherApiService
        {

            val requestInterceptor = Interceptor{chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.weatherapi.com/v1/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherApiService::class.java)
        }
    }
}