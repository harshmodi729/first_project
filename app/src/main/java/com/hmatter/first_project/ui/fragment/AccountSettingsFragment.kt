package com.hmatter.first_project.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.navigation.findNavController
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseFragment
import com.hmatter.first_project.extension.onDialogButtonClick
import com.hmatter.first_project.ui.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_account_settings.*
import kotlinx.android.synthetic.main.fragment_account_settings.ivDialogBg
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.lay_dialog_changepassword.*
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

        edChangePasswordLay.setOnClickListener {
            layAccountSettings.post {
                showChangePasswordDialog(layAccountSettings, ivDialogBg)
            }
        }

        onDialogButtonClick = {
            hideChangePasswordDialog(ivDialogBg)
        }
    }

}