package com.ss_eduhub.edupi.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ss_eduhub.edupi.R
import com.ss_eduhub.edupi.base.BaseActivity
import com.ss_eduhub.edupi.base.BaseResult
import com.ss_eduhub.edupi.extension.makeToast
import com.ss_eduhub.edupi.viewmodel.SplashViewModel

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