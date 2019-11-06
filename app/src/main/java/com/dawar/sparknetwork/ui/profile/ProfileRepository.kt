package com.dawar.sparknetwork.ui.profile

import com.dawar.sparknetwork.models.User
import com.dawar.sparknetwork.ui.main.BaseRepository


/**
 *  The repository handling database operations for profile flow.
 */
class ProfileRepository private constructor() : BaseRepository() {


    fun getUserProfile(id: String, onUser: ((User?) -> Unit)) {
        mAppDatabase.getUser(id, onUser)
    }

    fun saveProfile(user: User, onSuccess: ((Boolean) -> Unit)? = null) {
        mAppDatabase.saveUser(user, onSuccess)
    }


    companion object {
        private var instance: ProfileRepository? = null

        fun getInstance(): ProfileRepository {
            if (instance == null)
                instance = ProfileRepository()
            return instance!!
        }
    }
}