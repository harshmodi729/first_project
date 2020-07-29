package com.hmatter.first_project.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseActivity
import com.hmatter.first_project.base.BaseResult
import com.hmatter.first_project.extension.makeToast
import com.hmatter.first_project.viewmodel.AccountSettingsViewModel

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val accountSettingsViewModel =
            ViewModelProviders.of(this)[AccountSettingsViewModel::class.java]
//        accountSettingsViewModel.getUserProfileData(this)
        accountSettingsViewModel.userProfile.observe(this, Observer {
            when (it) {
                is BaseResult.Success -> {
                    Handler().run {
                        postDelayed(Runnable {
                            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                            finish()
                        }, 1500)
                    }
                }
                is BaseResult.Error -> {
                    makeToast(it.errorMessage)
                    Handler().run {
                        postDelayed(Runnable {
                            startActivity(Intent(this@SplashActivity, StartupActivity::class.java))
                            finish()
                        }, 1500)
                    }
                }
            }
        })
        Handler().run {
            postDelayed(Runnable {
                startActivity(Intent(this@SplashActivity, StartupActivity::class.java))
                finish()
            }, 1500)
        }
    }
}