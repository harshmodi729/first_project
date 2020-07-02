package com.hmatter.first_project.ui.activity

import android.os.Bundle
import android.text.method.LinkMovementMethod
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        tvToolbarTitle.text = getString(R.string.sign_up)
        chTerms.movementMethod = LinkMovementMethod.getInstance()
        btnToolbarBack.setOnClickListener {
            onBackPressed()
        }
    }
}