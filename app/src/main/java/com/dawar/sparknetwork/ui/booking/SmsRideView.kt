package com.dawar.sparknetwork.ui.booking

import com.dawar.sparknetwork.models.User

/**
 * View for SmsRideView.
 */
interface SmsRideView {
    fun onRideBooked()
    fun onError(s: String)
    fun onCustomerFound(user: User)
}