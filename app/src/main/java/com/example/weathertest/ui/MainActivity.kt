package com.example.weathertest.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.weathertest.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

private const val MY_PERMISSION_ACCESS_COARSE_LOCATION = 1

class MainActivity : AppCompatActivity(), KodeinAware
{
    override val kodein by closestKodein()
    private val fusedLocationProviderClient: FusedLocationProviderClient by instance()
    private val locationCallback = object: LocationCallback()
    {
        override fun onLocationResult(p0: LocationResult?)
        {
            super.onLocationResult(p0)
        }
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        /**
         * This is setting up the bottom navigation bar to switch between fragments
         */
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottom_nav.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this, navController)

        //This is asking the user for the prermission on the start up of the app
        requestLocationPermission()

        //This checks to see if the app has permission on start up
        if(hasLocationPermission())
        {
            bindLocationManager()
        }
        else
        {
            requestLocationPermission()
        }
    }

    private fun bindLocationManager()
    {
        LifecycleBoundLocationManager(
            this,
            fusedLocationProviderClient,
            locationCallback
        )
    }

    private fun hasLocationPermission(): Boolean
    {
        return ContextCompat.checkSelfPermission(this,
        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * This sets up the back button in the top corner of the app
     */
    override fun onSupportNavigateUp(): Boolean
    {
        return NavigationUI.navigateUp(navController, null)
    }

    private fun requestLocationPermission()
    {
        ActivityCompat.requestPermissions(this,
        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
        MY_PERMISSION_ACCESS_COARSE_LOCATION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    )
    {
       if(requestCode == MY_PERMISSION_ACCESS_COARSE_LOCATION)
       {
           if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
           {
               bindLocationManager()
           }
           else
           {
               Toast.makeText(this, "Please, set location manually in settings", Toast.LENGTH_LONG).show()
           }
       }
    }
}