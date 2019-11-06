package com.dawar.sparknetwork.ui.main

import com.dawar.sparknetwork.ui.main.database.AppDatabase

abstract class BaseRepository {
    protected val mAppDatabase = AppDatabase.getInstance()
}
