package com.example.weathertest.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * This creates a local scope that will be used for the three fragments
 */
abstract class ScopedFragment : Fragment(), CoroutineScope
{
    private lateinit var job: Job

    /**
     * This states that the jobs will be running on the Main thread
     */
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy()
    {
        super.onDestroy()
        job.cancel()
    }
}