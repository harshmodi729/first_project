package com.hmatter.first_project.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseActivity
import kotlinx.android.synthetic.main.lay_toolbar.*

class OTPActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_o_t_p)
        tvToolbarTitle.text = getString(R.string.otp_header_text)
        btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
    }
}