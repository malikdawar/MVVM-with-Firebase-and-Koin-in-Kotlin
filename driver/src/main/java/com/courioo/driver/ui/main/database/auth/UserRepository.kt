package com.courioo.driver.ui.main.database.auth


import com.courioo.driver.models.User
import com.courioo.driver.ui.main.BaseRepository


/**
 *  The repository handling database operations for profile flow.
 */
class UserRepository private constructor() : BaseRepository() {


    fun getUser(id: String, onUser: ((User?) -> Unit)) {
        mAppDatabase.getUser(id, onUser)
    }

    fun saveUser(user: User, onSuccess: ((Boolean) -> Unit)? = null) {
        mAppDatabase.saveUser(user, onSuccess)
    }


    companion object {
        private var instance: UserRepository? = null

        fun getInstance(): UserRepository {
            if (instance == null)
                instance =
                    UserRepository()
            return instance!!
        }
    }
}