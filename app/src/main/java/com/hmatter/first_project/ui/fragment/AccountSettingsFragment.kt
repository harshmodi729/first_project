package com.hmatter.first_project.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseFragment
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.extension.makeToast
import com.hmatter.first_project.extension.onChangePasswordDialogButtonClick
import com.hmatter.first_project.extension.onDialogButtonClick
import com.hmatter.first_project.ui.activity.StartupActivity
import com.hmatter.first_project.viewmodel.AccountSettingsViewModel
import com.hmatter.first_project.viewmodel.ForgotPasswordViewModel
import kotlinx.android.synthetic.main.fragment_account_settings.*
import kotlinx.android.synthetic.main.lay_account_setting_edit_detail.view.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class AccountSettingsFragment : BaseFragment(R.layout.fragment_account_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvToolbarTitle.text = getString(R.string.account_settings)

        val accountSettingsViewModel =
            ViewModelProviders.of(this)[AccountSettingsViewModel::class.java]
        val changePasswordViewModel =
            ViewModelProviders.of(this)[ForgotPasswordViewModel::class.java]

        accountSettingsViewModel.getUserProfileData(mContext)
        accountSettingsViewModel.profileLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is BaseResult.Success -> {
                    panelPhoneNumber.edChangeProfile.setText(it.item.mobile)
                    panelEmail.edChangeProfile.setText(it.item.email)
                    panelPassword.edChangeProfile.setText(it.item.password)
                }
                is BaseResult.Error -> {
                    mContext.makeToast(it.errorMessage)
                }
            }
        })
        accountSettingsViewModel.signOutLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is BaseResult.Success -> {
                    startActivity(
                        Intent(mContext, StartupActivity::class.java).putExtra(
                            "isAlreadyVisitIntro", true
                        )
                    )
                    mContext.finish()
                }
                is BaseResult.Error -> {
                    mContext.makeToast(it.errorMessage)
                }
            }
        })
        changePasswordViewModel.resetPasswordLiveData.observe(viewLifecycleOwner, Observer {
            hideProgressDialog(ivDialogBg)
            when (it) {
                is BaseResult.Success -> {
                    mContext.makeToast(it.item)
                    hideChangePasswordDialog(ivDialogBg)
                }
                is BaseResult.Error -> {
                    mContext.makeToast(it.errorMessage)
                }
            }
        })

        btnToolbarBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
        btnLogout.setOnClickListener {
            showLogoutDialog(layAccountSettings, ivDialogBg)
        }
        panelPhoneNumber.btnEdit.setOnClickListener {
            mContext.makeToast("Phone number edit")
        }
        panelEmail.btnEdit.setOnClickListener {
            mContext.makeToast("Email edit")
        }
        panelPassword.btnEdit.setOnClickListener {
            showChangePasswordDialog(layAccountSettings, ivDialogBg)
        }

        onDialogButtonClick = {
            hideLogoutDialog(ivDialogBg)
            if (it) {
                accountSettingsViewModel.userSignOut(mContext)
            }
        }

        onChangePasswordDialogButtonClick = { isPositive, forgotPasswordItem ->
            if (isPositive)
                changePasswordViewModel.resetPassword(mContext, forgotPasswordItem, false)
            else
                hideChangePasswordDialog(ivDialogBg)
        }
    }
}