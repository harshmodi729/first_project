package com.hmatter.first_project.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.hmatter.first_project.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().run {
            postDelayed(Runnable {
                startActivity(Intent(this@SplashActivity, IntroActivity::class.java))
                finish()
            }, 1500)
        }
    }
}