package com.hmatter.first_project.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseActivity
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.lay_reset_forgot_password.*
import kotlinx.android.synthetic.main.lay_toolbar.*
import kotlinx.android.synthetic.main.lay_username_forgot_password.*

class ForgotPasswordActivity : BaseActivity(), TextWatcher {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        tvToolbarTitle.text = getString(R.string.forgot_password)
        btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
        edUserName.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(
                textview: TextView?,
                actioId: Int,
                keyEvent: KeyEvent?
            ): Boolean {
                if (edUserName.text!!.isNotEmpty() && edUserName.text!!.isNotBlank()) {
                    if (actioId == EditorInfo.IME_ACTION_DONE) {
                        layUserName.visibility = View.GONE
                        layReset.visibility = View.VISIBLE
                        return true
                    }
                } else Toast.makeText(
                    this@ForgotPasswordActivity,
                    "Please enter username",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
        })
        edPassword.addTextChangedListener(this)
        edConfirmPassword.addTextChangedListener(this)
    }

    override fun afterTextChanged(p0: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (value.isNullOrEmpty()) {
            btnResetPassword.isEnabled = false
        } else {
            if (!btnResetPassword.isEnabled)
                btnResetPassword.isEnabled = true
        }
    }
}