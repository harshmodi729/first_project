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
import com.hmatter.first_project.viewmodel.SplashViewModel

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val splashViewModel =
            ViewModelProviders.of(this)[SplashViewModel::class.java]
        splashViewModel.isUserLogin(this)
        splashViewModel.isLoginLiveData.observe(this, Observer {
            Handler().postDelayed({
                when (it) {
                    is BaseResult.Success -> {
                        if (it.item.first) {
                            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                            finish()
                        } else {
                            startActivity(
                                Intent(this@SplashActivity, StartupActivity::class.java)
                                    .putExtra("isAlreadyVisitIntro", it.item.second)
                            )
                            finish()
                        }
                    }
                    is BaseResult.Error -> {
                        makeToast(it.errorMessage)
                        startActivity(
                            Intent(this@SplashActivity, StartupActivity::class.java)
                                .putExtra("isAlreadyVisitIntro", false)
                        )
                        finish()
                    }
                }
            }, 3000)
        })
    }
}