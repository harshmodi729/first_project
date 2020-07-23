package com.hmatter.first_project.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseFragment
import com.hmatter.first_project.extension.makeToast
import kotlinx.android.synthetic.main.fragment_account_settings.*
import kotlinx.android.synthetic.main.lay_account_setting_edit_detail.view.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class AccountSettingsFragment : BaseFragment(R.layout.fragment_account_settings) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvToolbarTitle.text = getString(R.string.account_settings)

        btnToolbarBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
        btnLogout.setOnClickListener {
            mContext.finish()
        }
        panelPhoneNumber.btnEdit.setOnClickListener {
            mContext.makeToast("Phone number edit")
        }
        panelEmail.btnEdit.setOnClickListener {
            mContext.makeToast("Email edit")
        }
        panelPassword.btnEdit.setOnClickListener {
            mContext.makeToast("Password edit")
        }
    }
}