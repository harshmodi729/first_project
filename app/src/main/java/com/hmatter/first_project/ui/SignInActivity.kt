package com.hmatter.first_project.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseActivity
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.lay_toolbar.*


class SignInActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btnToolbarBack.visibility = View.GONE
        tvToolbarTitle.text = getString(R.string.sign_in)
        tvSignUp.setOnClickListener { startActivity(Intent(this, SignUpActivity::class.java)) }
        tvForgotPassword.setOnClickListener { startActivity(Intent(this,ForgotPasswordActivity::class.java)) }
    }
}