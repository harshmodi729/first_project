package com.hmatter.first_project.ui.activity

import android.content.Intent
import android.os.Bundle
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseActivity
import com.hmatter.first_project.extension.makeToast
import kotlinx.android.synthetic.main.activity_o_t_p.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class OTPActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_o_t_p)

        tvToolbarTitle.text = getString(R.string.otp_header_text)
        btnToolbarBack.setOnClickListener {
            onBackPressed()
        }

        otpResend.setOnClickListener {
            makeToast("Otp resend request successfully.")
        }
        otp.setOtpCompletionListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}