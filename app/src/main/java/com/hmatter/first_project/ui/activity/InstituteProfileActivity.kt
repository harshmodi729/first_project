package com.hmatter.first_project.ui.activity

import android.os.Bundle
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseActivity
import kotlinx.android.synthetic.main.activity_institute_profile.*

class InstituteProfileActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_institute_profile)

        btnToolbarBack.setOnClickListener { onBackPressed() }
    }
}