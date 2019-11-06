package com.dawar.sparknetwork.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.dawar.sparknetwork.R
import com.dawar.sparknetwork.ui.profile.ProfileFragment
import com.dawar.sparknetwork.ui.profile.ProfileRepository
import com.dawar.sparknetwork.ui.profile.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainActivity : AppCompatActivity() {

    lateinit var profileFragment: ProfileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initKoin()
        setContentView(R.layout.main_activity)
        launchProfileFragment()

    }

    private fun initKoin() {
        val myModule = module {
            viewModel {
                ProfileViewModel(get())
            }
            single {
                ProfileRepository.getInstance()
            }

        }
        // start Koin!
        startKoin {
            // declare used Android context
            androidContext(applicationContext)
            // declare modules
            modules(myModule)
        }
    }

    private fun launchProfileFragment() {
        profileFragment = ProfileFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                profileFragment
            )
            .commitNow()
    }

}
