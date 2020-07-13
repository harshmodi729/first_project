package com.hmatter.first_project.ui.activity

import android.content.Intent
import android.os.Bundle
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseActivity
import kotlinx.android.synthetic.main.activity_account_settings.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class AccountSettingsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)
        tvToolbarTitle.text = getString(R.string.account_settings)

        btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
        btnLogout.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }
}