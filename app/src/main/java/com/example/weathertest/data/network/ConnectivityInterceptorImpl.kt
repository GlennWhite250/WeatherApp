package com.example.weathertest.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.example.weathertest.data.internal.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptorImpl(context: Context) : ConnectivityInterceptor
{
    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response
    {
        if (!isOnline())
        {
            //This is thrown when the device does not have a data connection
            throw NoConnectivityException()
        }
        //This allows the call to the api to occur
        return chain.proceed(chain.request())
    }

    /**
     * This checks to see if there is a data connection
     */
    private fun isOnline(): Boolean
    {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}