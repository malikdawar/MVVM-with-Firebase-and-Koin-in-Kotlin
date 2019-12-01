package com.courioo.driver.ui.main

import com.courioo.driver.ui.main.database.AppDatabase

abstract class BaseRepository {
    protected val mAppDatabase = AppDatabase.getInstance()
}
