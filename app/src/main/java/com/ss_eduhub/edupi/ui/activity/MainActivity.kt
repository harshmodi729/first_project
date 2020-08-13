package com.ss_eduhub.edupi.ui.activity

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ss_eduhub.edupi.R
import com.ss_eduhub.edupi.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationHost =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        NavigationUI.setupWithNavController(bottomNavigation, navigationHost.navController)

        navigationHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_account_settings,
                R.id.nav_app_settings -> bottomNavigation.visibility = View.GONE
                else -> bottomNavigation.visibility = View.VISIBLE
            }
        }
    }
}