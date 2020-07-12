package com.hmatter.first_project.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseActivity

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().run {
            postDelayed(Runnable {
                startActivity(Intent(this@SplashActivity, StartupActivity::class.java))
                finish()
            }, 1500)
        }
    }
}