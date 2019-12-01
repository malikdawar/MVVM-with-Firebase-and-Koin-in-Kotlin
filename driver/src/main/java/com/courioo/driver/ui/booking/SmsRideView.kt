package com.courioo.driver.ui.booking

import com.courioo.driver.models.SmsRide
import com.courioo.driver.models.User

/**
 * View for SmsRideView.
 */
interface SmsRideView {
    fun onError(s: String)
    fun onUserFound(user: User)
    fun onRideRequest(smsRide: SmsRide)
}