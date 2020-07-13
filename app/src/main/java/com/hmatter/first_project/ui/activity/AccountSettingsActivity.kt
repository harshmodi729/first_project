package com.hmatter.first_project.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hmatter.first_project.R
import kotlinx.android.synthetic.main.lay_toolbar.*

class AccountSettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)
        tvToolbarTitle.text = getString(R.string.account_settings)
    }
}