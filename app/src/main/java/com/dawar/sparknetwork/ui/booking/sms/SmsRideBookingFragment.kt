package com.dawar.sparknetwork.ui.booking.sms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dawar.sparknetwork.R
import com.dawar.sparknetwork.models.SmsRide
import com.dawar.sparknetwork.models.User
import com.dawar.sparknetwork.ui.booking.RideViewModel
import com.dawar.sparknetwork.ui.booking.SmsRideView
import com.dawar.sparknetwork.ui.main.BaseFragment
import com.dawar.sparknetwork.ui.main.MainActivity
import kotlinx.android.synthetic.main.sms_ride_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SmsRideBookingFragment : BaseFragment(),
    SmsRideView {
    private val mRideViewModel: RideViewModel by viewModel()
    private val gpsLocation by lazy {
        (activity as MainActivity).gpsLocation
    }

    companion object {
        fun newInstance() = SmsRideBookingFragment()
    }

    private fun showToast(s: String?) = Toast.makeText(context, s, Toast.LENGTH_SHORT).show()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mRideViewModel.apply {
            getRideBookedData().observe(this@SmsRideBookingFragment, mUserDataUpdateObserver)
            getRideBookingData().observe(this@SmsRideBookingFragment, mRideRequestObserver)
            getUserData().observe(this@SmsRideBookingFragment, mUserObserver)
            getErrorData().observe(this@SmsRideBookingFragment, mErrorObserver)
            attachView(this@SmsRideBookingFragment)
            getUser("1")

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
        btnSubmit.setOnClickListener {
            submitRequest()
        }
        mRideViewModel.attachRideListener()
    }

    private fun submitRequest() {
        enableSubmitButton(false)
        val smsRide = SmsRide(gpsLocation.getUserLocation())
        mRideViewModel.newRideRequest(smsRide)
    }

    private fun enableSubmitButton(enabled: Boolean = true) {
        btnSubmit.isEnabled = enabled
        btnSubmit.setText(if (enabled) R.string.book else R.string.booking)
    }

    override fun onRideBooked() {
        enableSubmitButton()
        showToast(getString(R.string.success))
    }

    override fun onError(s: String) {
        showToast(s)
        enableSubmitButton()
    }

    override fun onUserFound(user: User) {
        showToast("Welcome ${user.name}")
    }

    override fun onRideListener(smsRide: SmsRide) {

        val msg = when (smsRide.rideStatus) {
            0 -> "RIDE REQUESTED"
            1 -> "RIDE ACCEPTED"
            2 -> "RIDE PICKED"
            3 -> "RIDE COMPLETE"
            else -> "RIDE COMPLETED"
        }
        etName.setText(msg)
    }
}
