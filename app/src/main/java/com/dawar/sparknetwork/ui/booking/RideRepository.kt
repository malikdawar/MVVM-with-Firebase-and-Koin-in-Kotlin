package com.dawar.sparknetwork.ui.booking

import com.dawar.sparknetwork.models.SmsRide
import com.dawar.sparknetwork.ui.main.BaseRepository

class RideRepository private constructor() : BaseRepository() {

    fun newRideBySms(smsRide: SmsRide, onSuccess: ((Boolean, String?) -> Unit)? = null) {
        mAppDatabase.createRideRequestBySms(smsRide, onSuccess)
    }

    fun attachRideListener(onRideRequest: (SmsRide) -> Unit) {
        mAppDatabase.attachSmsRideListener(onRideRequest)
    }


    companion object {
        private var rideRepository: RideRepository? = null

        fun getInstance(): RideRepository {
            if (rideRepository == null)
                rideRepository = RideRepository()
            return rideRepository!!
        }
    }
}