package com.dawar.sparknetwork.ui.main

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dawar.sparknetwork.R
import com.dawar.sparknetwork.ui.booking.RideRepository
import com.dawar.sparknetwork.ui.booking.RideViewModel
import com.dawar.sparknetwork.ui.booking.sms.SmsRideBookingFragment
import com.dawar.sparknetwork.ui.main.database.auth.UserRepository
import com.dawar.sparknetwork.ui.main.utils.GPSLocation
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainActivity : AppCompatActivity() {

    lateinit var smsRideBookingFragment: SmsRideBookingFragment
    lateinit var gpsLocation: GPSLocation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initKoin()
        setContentView(R.layout.main_activity)
        launchProfileFragment()
        gpsLocation = GPSLocation(this)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED)) {
            finish()
        }
    }

    private fun initKoin() {
        val myModule = module {
            viewModel {
                RideViewModel(get(), get())
            }
            single {
                UserRepository.getInstance()
            }
            single {
                RideRepository.getInstance()
            }

        }
        // start Koin!
        startKoin {
            // declare used Android context
            androidContext(applicationContext)
            // declare modules
            modules(myModule)
        }
    }

    private fun launchProfileFragment() {
        smsRideBookingFragment = SmsRideBookingFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                smsRideBookingFragment
            )
            .commitNow()
    }

}
