package com.ss_eduhub.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ss_eduhub.R
import com.ss_eduhub.base.BaseActivity
import com.ss_eduhub.base.BaseResult
import com.ss_eduhub.extension.makeToastForServerError
import com.ss_eduhub.viewmodel.SplashViewModel

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val splashViewModel =
            ViewModelProvider(this)[SplashViewModel::class.java]
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
                        makeToastForServerError(it)
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