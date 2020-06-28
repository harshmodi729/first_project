package com.hmatter.first_project.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hmatter.first_project.R
import kotlinx.android.synthetic.main.lay_toolbar.*

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        btnToolbarBack.visibility = View.GONE



    }
}