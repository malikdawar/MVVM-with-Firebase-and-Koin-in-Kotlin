package com.courioo.driver.ui.main.database

import com.courioo.driver.models.SmsRide
import com.courioo.driver.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class AppDatabase private constructor() {

    private val database = FirebaseDatabase.getInstance()
    private val dbRootRef = database.reference
    private val usersNode = dbRootRef.child("Users")
    private val ridesNode by lazy { usersNode.child(userId).child("rides") }
    private val rideStatusNode by lazy { ridesNode.child("SmsRide").child("rideStatus") }
    private lateinit var onUser: ((User?) -> Unit)
    private lateinit var onRideRequest: (SmsRide) -> Unit
    private lateinit var userId: String

    private val mUserValueEventListener = object : ValueEventListener {
        override fun onDataChange(p0: DataSnapshot) {
            val user = p0.child(userId).getValue(User::class.java)
            onUser(user)
            close()

        }

        override fun onCancelled(p0: DatabaseError) {
            onUser(null)
            close()

        }
    }

    private val mRidesValueEventListener = object : ValueEventListener {
        override fun onDataChange(p0: DataSnapshot) {
            val smsRide = p0.child("SmsRide").getValue(SmsRide::class.java)
            smsRide?.apply {
                onRideRequest(this)
            }


        }

        override fun onCancelled(p0: DatabaseError) {
            p0.toException().printStackTrace()

        }
    }

    fun saveUser(user: User, onSuccess: ((Boolean) -> Unit)? = null) {

        usersNode.child(user.id).setValue(user)
            .addOnCompleteListener {
                onSuccess?.invoke(it.isSuccessful)
            }
    }


    fun getUser(id: String, onUser: ((User?) -> Unit)) {
        this.onUser = onUser
        this.userId = id
        usersNode.addValueEventListener(mUserValueEventListener)

    }


    fun attachSmsRideListener(onRideRequest: (SmsRide) -> Unit) {
        userId = "1" // customer to fetch
        this.onRideRequest = onRideRequest
        ridesNode.addValueEventListener(mRidesValueEventListener)
    }

    fun updateRideStatus(status: Int) {
        rideStatusNode.setValue(status)

    }

    private fun close() {
        usersNode.removeEventListener(mUserValueEventListener)
    }

    companion object {
        private var appDatabase: AppDatabase? = null

        fun getInstance(): AppDatabase {
            if (appDatabase == null)
                appDatabase =
                    AppDatabase()
            return appDatabase!!
        }
    }

}