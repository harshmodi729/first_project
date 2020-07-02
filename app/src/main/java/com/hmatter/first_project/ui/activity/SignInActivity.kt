package com.hmatter.first_project.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
        tvForgotPassword.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ForgotPasswordActivity::class.java
                )
            )
        }
        btnSignIn.setOnClickListener {
            if (edUserName.text!!.isNotEmpty() && edPassword.text!!.isNotEmpty()) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Please enter valid username and password.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}