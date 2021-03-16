package com.example.weathertest.ui.weather.current

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.weathertest.R
import com.example.weathertest.data.internal.glide.GlideApp
import com.example.weathertest.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {
    //This should bring over the Kodeins from the ForecastApplication (the one with the onBinds()
    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        val weatherLocation = viewModel.weatherLocation.await()
        weatherLocation.observe(viewLifecycleOwner, Observer { location->
            if (location == null)
            {
                return@Observer
            }
            updateLocation(location.name)
        })
        currentWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                return@Observer
            }
            //This is setting the Loading screen invisiable if there is info on the screen
            progressBar_loading.visibility = View.GONE
            tv_loading.visibility = View.GONE


            updateDateToToday()
            updateTemperatures(it.temperature)
            updateCondition(it.conditionText)
            updatePrecipitation(it.precipitationVolume)
            updateUV(it.uv)
            updateWindSpeed(it.windSpeed)
            updateWindDirection(it.windDirection)
            updatHumidity(it.humidity)

            //Glide implitation that loads the icons into the image view
            GlideApp.with(this@CurrentWeatherFragment)
                .load("http:${it.conditionIconUrl}")
                .into(iv_condition_icon)
        })
    }

    /**
     * This allows us to not have a confilct of the variable unitAbbreviation
     */
    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        return if (viewModel.isMetricUnit) {
            metric
        } else {
            imperial
        }
    }

    /**
     * This is a helper function to udpate the UI
     */
    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    /**
     * This helper function is setting the temperatures and picking between metric and imperial
     */
    private fun updateTemperatures(temperature: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")

        //This is setting the Current temp to the layout
        temp.text = "$temperature$unitAbbreviation"
    }

    private fun updateCondition(condition: String) {
        status.text = condition
    }

    private fun updateWindDirection(windDirection: String)
    {
        tv_wind_dir.text = "$windDirection"
    }


    private fun updatePrecipitation(precipitation: Double)
    {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("mm", "in")
        tv_precipitation.text = "$precipitation$unitAbbreviation"
    }

    private fun updateWindSpeed(windSpeed: Double)
    {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph", "mph")
        tv_wind_speed.text = "$windSpeed$unitAbbreviation"
    }

    private fun updatHumidity(humidity: Int)
    {
        tv_humidity.text = "$humidity"
    }

    private fun updateUV(uv: Double)
    {
        tv_uv.text = "$uv"
    }
}