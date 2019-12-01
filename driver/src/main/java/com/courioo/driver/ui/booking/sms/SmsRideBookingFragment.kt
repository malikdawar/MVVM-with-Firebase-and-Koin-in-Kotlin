package com.courioo.driver.ui.booking.sms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.courioo.driver.R
import com.courioo.driver.models.SmsRide
import com.courioo.driver.models.User
import com.courioo.driver.ui.booking.DriverViewModel
import com.courioo.driver.ui.booking.SmsRideView
import com.courioo.driver.ui.main.BaseFragment
import com.courioo.driver.ui.main.MainActivity
import kotlinx.android.synthetic.main.sms_ride_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SmsRideBookingFragment : BaseFragment(),
    SmsRideView {
    private val mDriverViewModel: DriverViewModel by viewModel()
    private val gpsLocation by lazy {
        (activity as MainActivity).gpsLocation
    }

    companion object {
        fun newInstance() = SmsRideBookingFragment()
    }

    private fun showToast(s: String?) = Toast.makeText(context, s, Toast.LENGTH_SHORT).show()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDriverViewModel.apply {
            getRideBookedData().observe(this@SmsRideBookingFragment, mRideRequestObserver)
            getUserData().observe(this@SmsRideBookingFragment, mUserObserver)
            getErrorData().observe(this@SmsRideBookingFragment, mErrorObserver)
            attachView(this@SmsRideBookingFragment)
            getUser("2")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.sms_ride_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnAccept.setOnClickListener {
            submitRequest()
        }
        updateButton()
        mDriverViewModel.saveDriver(gpsLocation.getUserLocation())
    }

    private fun submitRequest() {
        when (btnAccept.text) {
            "ACCEPT" -> mDriverViewModel.updateRideStatus(1)
            "RIDE ACCEPTED" -> mDriverViewModel.updateRideStatus(2)
            "RIDE PICKED" -> mDriverViewModel.updateRideStatus(3)
            "RIDE COMPLETE" -> mDriverViewModel.updateRideStatus(-1)
        }
    }

    private fun updateButton(status: Int = -1) {
        btnAccept.text = when (status) {
            0 -> "ACCEPT"
            1 -> "RIDE ACCEPTED"
            2 -> "RIDE PICKED"
            3 -> "RIDE COMPLETE"
            else -> "ONLINE"
        }
    }


    override fun onRideRequest(smsRide: SmsRide) {
        updateButton(smsRide.rideStatus)
    }


    override fun onError(s: String) {
        showToast(s)
    }

    override fun onUserFound(user: User) {
        showToast("Welcome ${user.name}")
    }
}
