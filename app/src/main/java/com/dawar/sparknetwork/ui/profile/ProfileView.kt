package com.dawar.sparknetwork.ui.profile

import com.dawar.sparknetwork.models.User

/**
 * View for ChangePhoneNumberFragment.
 */
interface ProfileView {
    fun onUserSaved(success: Boolean)
    fun onUser(user: User?)
}