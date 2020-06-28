package com.hmatter.first_project

import android.os.Bundle
import android.view.View
import com.hmatter.first_project.base.BaseActivity
import kotlinx.android.synthetic.main.lay_toolbar.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnToolbarBack.visibility = View.GONE
        ivUserProfile.visibility = View.VISIBLE
        tvToolbarTitle.text = getString(R.string.home)
    }
}