package com.courioo.driver.ui.booking

import com.courioo.driver.models.SmsRide
import com.courioo.driver.ui.main.BaseRepository

class RideRepository private constructor() : BaseRepository() {

    fun attachNewRideListener(onRideRequest: (SmsRide) -> Unit) {
        mAppDatabase.attachSmsRideListener(onRideRequest)
    }

    fun updateRideStatus(status: Int) {
        mAppDatabase.updateRideStatus(status)
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