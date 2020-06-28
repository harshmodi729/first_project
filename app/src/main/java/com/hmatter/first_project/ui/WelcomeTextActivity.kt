package com.hmatter.first_project.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseActivity

class WelcomeTextActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_text)

        Handler().run {
            postDelayed(Runnable {
                startActivity(Intent(this@WelcomeTextActivity, IntroActivity::class.java))
                finish()
            }, 1500)
        }
    }
}