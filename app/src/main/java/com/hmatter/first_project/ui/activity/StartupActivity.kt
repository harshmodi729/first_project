package com.hmatter.first_project.ui.activity

import android.os.Bundle
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseActivity


class StartupActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)
    }
}