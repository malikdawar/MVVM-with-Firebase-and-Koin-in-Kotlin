package com.dawar.sparknetwork.ui.main.database

import com.dawar.sparknetwork.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AppDatabase private constructor() {

    private val database = FirebaseDatabase.getInstance()
    private val dbRootRef = database.reference
    private val userNode = dbRootRef.child("Users")
    private lateinit var onUser: ((User?) -> Unit)
    private lateinit var userId: String

    private val valueEventListener = object : ValueEventListener {
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

    fun saveUser(user: User, onSuccess: ((Boolean) -> Unit)? = null) {

        userNode.child("SN-1").setValue(user)
            .addOnCompleteListener {
                onSuccess?.invoke(it.isSuccessful)
            }
    }


    fun getUser(id: String, onUser: ((User?) -> Unit)) {
        this.onUser = onUser
        this.userId = id
        userNode.addValueEventListener(valueEventListener)

    }

    private fun close() {
        userNode.removeEventListener(valueEventListener)
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