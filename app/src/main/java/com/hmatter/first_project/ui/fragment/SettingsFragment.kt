package com.hmatter.first_project.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseFragment
import com.hmatter.first_project.ui.activity.AccountSettingsActivity
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnToolbarBack.visibility = View.GONE
        tvToolbarTitle.text = getString(R.string.settings)
        tvAccountSetting.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    AccountSettingsActivity::class.java
                )
            )
        }
    }
}