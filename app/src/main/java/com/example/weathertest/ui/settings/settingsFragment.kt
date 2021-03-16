package com.example.weathertest.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.example.weathertest.R

class settingsFragment : PreferenceFragmentCompat()
{
    /**
     * This is setting the fragment to the preferences file under the XML package
     */
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?)
    {
        addPreferencesFromResource(R.xml.preferences)
    }

    /**
     * This is setting up the action bar to display Settings
     */
    override fun onActivityCreated(savedInstanceState: Bundle?)
    {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Settings"
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = null
    }
}