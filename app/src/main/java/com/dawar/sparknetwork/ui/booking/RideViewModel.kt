package com.dawar.sparknetwork.ui.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dawar.sparknetwork.models.SmsRide
import com.dawar.sparknetwork.models.User
import com.dawar.sparknetwork.ui.main.BaseViewModel
import com.dawar.sparknetwork.ui.main.database.auth.UserRepository

class RideViewModel(
    private val mUserRepository: UserRepository
    , private val mRideRepository: RideRepository
) :
    BaseViewModel<SmsRideView>() {

    init {
        mUserRepository.saveUser(User("Careem User"))
    }

    private val mUserData = MutableLiveData<User>()
    private val mRideBooked = MutableLiveData<Boolean>()
    private val mError = MutableLiveData<String>()
    private val mRideRequestData = MutableLiveData<SmsRide>()
    fun getUserData() = mUserData as LiveData<User?>

    fun getRideBookedData() = mRideBooked as LiveData<Boolean>

    fun getRideBookingData() = mRideRequestData as LiveData<SmsRide>

    fun getErrorData() = mError as LiveData<String>

    val mErrorObserver: Observer<in String> = Observer {
        getView().onError(it)
    }
    val mUserDataUpdateObserver: Observer<in Boolean> = Observer {
        if (it)
            getView().onRideBooked()
        else mError.postValue("Failed")
    }

    val mRideRequestObserver: Observer<in SmsRide> = Observer {
        getView().onRideListener(it)
    }

    val mUserObserver: Observer<in User?> = Observer {
        if (it != null)
            getView().onUserFound(it)
        else mError.postValue("Customer not found")
    }

    fun newRideRequest(smsRide: SmsRide) {
        mRideRepository.newRideBySms(smsRide) { success, error ->
            if (success)
                mRideBooked.postValue(true)
            else mError.postValue(error)
        }
    }

    fun attachRideListener() = mRideRepository.attachRideListener {
        mRideRequestData.postValue(it)
    }

    fun getUser(id: String) {

        mUserRepository.getUser(id) {
            if (it == null)
                mError.postValue("Customer not found")
            else
                mUserData.postValue(it)
        }
    }
}