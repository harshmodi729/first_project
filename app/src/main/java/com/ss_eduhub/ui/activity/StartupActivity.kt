package com.ss_eduhub.ui.activity

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.ss_eduhub.R
import com.ss_eduhub.base.BaseActivity
import kotlinx.android.synthetic.main.activity_startup.*


class StartupActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)

        val navHostFragment = navHostStartup as NavHostFragment
        val graphInflater = navHostFragment.navController.navInflater
        val navGraph = graphInflater.inflate(R.navigation.nav_edupi_startup)
        val navController = navHostFragment.navController

        val destination = if (intent.getBooleanExtra("isAlreadyVisitIntro", false))
            R.id.nav_sign_in else R.id.nav_welcome
        navGraph.startDestination = destination
        navController.graph = navGraph
    }
}