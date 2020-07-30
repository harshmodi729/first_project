package com.hmatter.first_project.ui.fragment.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseFragment
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.extension.makeToast
import com.hmatter.first_project.viewmodel.AccountSettingsViewModel
import kotlinx.android.synthetic.main.fragment_user_profile.*

class UserProfileFragment : BaseFragment(R.layout.fragment_user_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val accountSettingsViewModel =
            ViewModelProviders.of(this)[AccountSettingsViewModel::class.java]
        accountSettingsViewModel.getUserProfileData(mContext)
        accountSettingsViewModel.userProfile.observe(viewLifecycleOwner, Observer {
            when (it) {
                is BaseResult.Success -> {
                    tvToolbarTitle.text = it.item.name
                }
                is BaseResult.Error -> {
                    mContext.makeToast(it.errorMessage)
                }
            }
        })
    }
}