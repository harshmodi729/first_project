package com.hmatter.first_project.ui.fragment

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
import com.hmatter.first_project.viewmodel.AccountSettingsViewModel
import kotlinx.android.synthetic.main.fragment_account_settings.*
import kotlinx.android.synthetic.main.lay_account_setting_edit_detail.view.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class AccountSettingsFragment : BaseFragment(R.layout.fragment_account_settings) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvToolbarTitle.text = getString(R.string.account_settings)

        val accountSettingsViewModel =
            ViewModelProviders.of(this)[AccountSettingsViewModel::class.java]
        accountSettingsViewModel.changePasswordDialogButtonClick.observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    is BaseResult.Success -> {
                        mContext.makeToast(it.item as String)
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
                mContext.finish()
            }
        }

        onChangePasswordDialogButtonClick = { isPositive, dialogView ->
            if (isPositive)
                accountSettingsViewModel.validationControl(dialogView)
            else
                hideChangePasswordDialog(ivDialogBg)
        }
    }
}