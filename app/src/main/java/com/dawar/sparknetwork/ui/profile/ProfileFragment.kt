package com.dawar.sparknetwork.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.dawar.sparknetwork.R
import com.dawar.sparknetwork.models.User
import com.dawar.sparknetwork.ui.main.BaseFragment
import kotlinx.android.synthetic.main.profile_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment(), ProfileView {
    val mProfileViewModel: ProfileViewModel by viewModel()

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // mProfileViewModel = ViewModelProviders.of(this)[ProfileViewModel::class.java]
        mProfileViewModel.attachView(this, this)
        mProfileViewModel.getProfile("SN-1")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnSubmit.setOnClickListener {
            submitUser()
        }
        enableSubmitButton(false)
    }

    private fun submitUser() {
        val user = User(etName.text.toString())
        user.address = etAddress.text.toString()
        user.email = etEmail.text.toString()
        user.phone = etPhone.text.toString()
        mProfileViewModel.saveProfile(user)
        enableSubmitButton(false)


    }

    private fun enableSubmitButton(enabled: Boolean) {
        btnSubmit.isEnabled = enabled
        btnSubmit.setText(if (enabled) R.string.submit else R.string.loading)
    }

    override fun onUser(user: User?) {
        etAddress.setText(user?.address)
        etName.setText(user?.name)
        etPhone.setText(user?.phone)
        etEmail.setText(user?.email)
        btnSubmit.setText(R.string.submit)
        enableSubmitButton(true)
    }

    override fun onUserSaved(success: Boolean) {
        enableSubmitButton(true)
    }
}
