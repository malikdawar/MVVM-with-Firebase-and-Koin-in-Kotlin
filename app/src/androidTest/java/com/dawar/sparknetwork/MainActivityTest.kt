package com.dawar.sparknetwork

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.dawar.sparknetwork.models.User
import com.dawar.sparknetwork.ui.main.MainActivity
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testSubmitButton() {
        clickView(R.id.btnSubmit)


    }

    @Test
    fun testEmailField() {
        fillField(R.id.etEmail, "dawarMalik@couriootechnologies.com")
    }

    @Test
    fun testNameField() {
        fillField(R.id.etName, "Malik Dawar")
    }

    @Test
    fun testPhoneField() {
        fillField(R.id.etPhone, "03217004140")
    }

    @Test
    fun testAddressField() {
        fillField(R.id.etAddress, "Lahore Pakistan")
    }

    @Test
    fun insertNewUser() {
        val mainActivity = activityRule.activity as MainActivity
        val user = User("Mobin").apply {
            address = "Lahore"
            email = "mm@gmail.com"
            phone = "03217004104"
        }
        // mainActivity.smsRideBookingFragment.mUserViewModel.saveProfile(user)

    }

    private fun fillField(@IdRes id: Int, text: String) {
        val mainActivity = activityRule.activity as MainActivity
        Espresso.onView(ViewMatchers.withId(id))
            .inRoot(RootMatchers.withDecorView(Matchers.`is`(mainActivity.window.decorView)))
            .perform(ViewActions.typeText(text))
    }

    private fun clickView(@IdRes id: Int) {
        val mainActivity = activityRule.activity as MainActivity
        Espresso.onView(ViewMatchers.withId(id))
            .inRoot(RootMatchers.withDecorView(Matchers.`is`(mainActivity.window.decorView)))
            .perform(ViewActions.click())
    }
}