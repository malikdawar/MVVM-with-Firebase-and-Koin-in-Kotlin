package com.courioo.driver.ui.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.courioo.driver.models.Location
import com.courioo.driver.models.SmsRide
import com.courioo.driver.models.User
import com.courioo.driver.ui.main.BaseViewModel
import com.courioo.driver.ui.main.database.auth.UserRepository

class DriverViewModel(
    private val mUserRepository: UserRepository,
    private val mRideRepository: RideRepository
) :
    BaseViewModel<SmsRideView>() {


    private val mUserData = MutableLiveData<User>()
    private val mRideRequestData = MutableLiveData<SmsRide>()
    private val mError = MutableLiveData<String>()

    fun getUserData() = mUserData as LiveData<User?>

    fun getRideBookedData() = mRideRequestData as LiveData<SmsRide>

    fun getErrorData() = mError as LiveData<String>

    val mErrorObserver: Observer<in String> = Observer {
        getView().onError(it)
    }
    val mRideRequestObserver: Observer<in SmsRide> = Observer {
        getView().onRideRequest(it)
    }

    val mUserObserver: Observer<in User?> = Observer {
        if (it != null)
            getView().onUserFound(it)
        else mError.postValue("User not found")
    }

    init {
        attachNewRideListener()
    }

    private fun attachNewRideListener() {
        mRideRepository.attachNewRideListener { smsRide ->
            mRideRequestData.postValue(smsRide)
        }
    }

    fun updateRideStatus(status: Int) {
        mRideRepository.updateRideStatus(status)
    }

    fun saveDriver(location: Location) {
        mUserRepository.saveUser(User().apply {
            name = "Careem Driver"
            this.location = location
        })
    }

    fun getUser(id: String) {

        mUserRepository.getUser(id) {
            if (it == null)
                mError.postValue("User not found")
            else
                mUserData.postValue(it)
        }
    }
}