package com.example.weathertest

import android.app.Application
import android.content.Context
import android.system.Os.bind
import androidx.preference.PreferenceManager
import com.example.weathertest.data.OpenWeatherApiService
import com.example.weathertest.data.db.ForecastDatabase
import com.example.weathertest.data.network.ConnectivityInterceptor
import com.example.weathertest.data.network.ConnectivityInterceptorImpl
import com.example.weathertest.data.network.WeatherNetworkDataSource
import com.example.weathertest.data.network.WeatherNetworkDataSourceImpl
import com.example.weathertest.data.provider.LocationProvider
import com.example.weathertest.data.provider.LocationProviderImpl
import com.example.weathertest.data.provider.UnitProvider
import com.example.weathertest.data.provider.UnitProviderImpl
import com.example.weathertest.data.repository.ForecastRepository
import com.example.weathertest.data.repository.ForecastRepositoryImpl
import com.example.weathertest.ui.weather.current.CurrentWeatherViewModelFactory
import com.example.weathertest.ui.weather.future.detail.FutureDetailViewModelFactory
import com.example.weathertest.ui.weather.future.list.FutureListWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*
import org.threeten.bp.LocalDate

class ForecastApplication : Application(), KodeinAware {
    /**
     * This is used (Kodein) for depency injection
     */
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().futureWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { OpenWeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(), instance()) }
        bind<ForecastRepository>() with singleton {
            ForecastRepositoryImpl(
                instance(),
                instance(),
                instance(),
                instance(),
                instance()
            )
        }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
        bind() from provider { FutureListWeatherViewModelFactory(instance(), instance()) }
        bind() from factory { detailDate: LocalDate ->
            FutureDetailViewModelFactory(
                detailDate,
                instance(),
                instance()
            )
        }
    }


    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}
