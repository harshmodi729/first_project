package com.ss_eduhub.ui.activity

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ss_eduhub.R
import com.ss_eduhub.base.BaseActivity
import com.ss_eduhub.ui.fragment.main.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), HomeFragment.OnViewMoreClickListener {
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

    override fun onViewMoreTagClick() {
        bottomNavigation.selectedItemId = R.id.nav_search
    }
}