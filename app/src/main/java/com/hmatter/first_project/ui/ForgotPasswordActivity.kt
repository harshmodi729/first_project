package com.hmatter.first_project.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hmatter.first_project.R
import kotlinx.android.synthetic.main.lay_toolbar.*

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        tvToolbarTitle.text = getString(R.string.forgot_password)
        btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
    }
}