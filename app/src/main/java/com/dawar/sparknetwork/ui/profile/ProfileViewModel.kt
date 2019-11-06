package com.dawar.sparknetwork.ui.profile

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dawar.sparknetwork.models.User
import com.dawar.sparknetwork.ui.main.BaseViewModel

class ProfileViewModel(private val mProfileRepository: ProfileRepository) : BaseViewModel<ProfileView>() {


    private val mMutableProfileData = MutableLiveData<User?>()
    private val mProfileDataSaved = MutableLiveData<Boolean>()
    private val mUserObserver: Observer<in User?> = Observer {
        getView().onUser(it)
    }
    private val mUserDataUpdateObserver: Observer<in Boolean> = Observer {
        getView().onUserSaved(it)
    }

    fun getProfile(id: String) {

        mProfileRepository.getUserProfile(id) {
            mMutableProfileData.postValue(it)
        }
    }

    fun saveProfile(user: User) {
        mProfileRepository.saveProfile(user) {
            mProfileDataSaved.postValue(it)
        }
    }

    override fun attachView(view: ProfileView, lifecycleOwner: LifecycleOwner) {
        super.attachView(view, lifecycleOwner)

        mMutableProfileData.observe(lifecycleOwner, mUserObserver)

        mProfileDataSaved.observe(lifecycleOwner, mUserDataUpdateObserver)

    }


    override fun onCleared() {
        super.onCleared()
        mMutableProfileData.removeObserver(mUserObserver)
        mProfileDataSaved.removeObserver(mUserDataUpdateObserver)

    }
}