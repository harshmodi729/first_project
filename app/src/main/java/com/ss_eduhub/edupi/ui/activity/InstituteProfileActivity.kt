package com.ss_eduhub.edupi.ui.activity

import android.os.Bundle
import com.ss_eduhub.edupi.R
import com.ss_eduhub.edupi.base.BaseActivity
import com.ss_eduhub.edupi.common.Constants
import kotlinx.android.synthetic.main.activity_institute_profile.*

class InstituteProfileActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_institute_profile)

        if (intent.extras != null) {
            tvInstituteName.text = intent.getStringExtra(Constants.INSTITUTE_NAME)
        }
        btnToolbarBack.setOnClickListener { onBackPressed() }
    }
}