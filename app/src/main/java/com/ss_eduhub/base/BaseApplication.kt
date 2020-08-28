package com.ss_eduhub.base

import android.app.Application
import com.facebook.stetho.Stetho
import com.ss_eduhub.BuildConfig

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)
    }
}