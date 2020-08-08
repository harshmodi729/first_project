package com.hmatter.first_project.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hmatter.first_project.R
import kotlinx.android.synthetic.main.lay_toolbar.*

class InstituteProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_institute_profile)
        tvToolbarTitle.text = getString(R.string.information)
    }
}