package com.sseduhub.edupi.ui.activity

import android.os.Bundle
import com.sseduhub.edupi.R
import com.sseduhub.edupi.base.BaseActivity
import kotlinx.android.synthetic.main.activity_institute_profile.*

class InstituteProfileActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_institute_profile)

        btnToolbarBack.setOnClickListener { onBackPressed() }
    }
}