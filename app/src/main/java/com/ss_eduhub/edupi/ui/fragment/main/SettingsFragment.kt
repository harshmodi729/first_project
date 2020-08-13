package com.ss_eduhub.edupi.ui.fragment.main

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.ss_eduhub.edupi.R
import com.ss_eduhub.edupi.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnToolbarBack.visibility = View.GONE
        tvToolbarTitle.text = getString(R.string.settings)
        tvAccountSetting.setOnClickListener {
            it.findNavController().navigate(R.id.action_nav_settings_to_nav_account_settings)
        }
        tvAppSettings.setOnClickListener {
            it.findNavController().navigate(R.id.action_nav_settings_to_nav_app_settings)
        }
    }
}