package com.dawar.sparknetwork.ui.main.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.util.concurrent.TimeUnit

class GPSLocation(private val activity: Activity) : LocationListener {
    init {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            activity.requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 3)
        }
    }

    private val locationManager =
        activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @SuppressLint("MissingPermission")
    private var location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

    init {
        getLocation()
    }

    fun getUserLocation() =
        com.dawar.sparknetwork.models.Location(location?.latitude, location?.longitude)


    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                TimeUnit.SECONDS.toMillis(5),
                10f,
                this
            )
        } else Toast.makeText(activity, "Please enable GPS.", Toast.LENGTH_SHORT).show()
    }

    override fun onLocationChanged(p0: Location?) {
        this.location = p0
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

    }

    override fun onProviderEnabled(p0: String?) {

    }

    override fun onProviderDisabled(p0: String?) {

    }
}