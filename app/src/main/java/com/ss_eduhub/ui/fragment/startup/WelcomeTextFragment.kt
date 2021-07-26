package com.ss_eduhub.ui.fragment.startup

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ss_eduhub.R
import com.ss_eduhub.base.BaseFragment

class WelcomeTextFragment : BaseFragment(R.layout.fragment_welcome_text) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenResumed {
            Handler(Looper.getMainLooper()).run {
                postDelayed({
                    findNavController().navigate(R.id.action_nav_welcome_to_nav_exam_dashboard)
                }, 1500)
            }
        }
    }
}