package com.hmatter.first_project.ui.fragment

import android.os.Bundle
import android.os.Handler
import androidx.navigation.fragment.findNavController
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseFragment

class WelcomeTextFragment : BaseFragment(R.layout.fragment_welcome_text) {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Handler().run {
            postDelayed({
                findNavController().navigate(R.id.action_nav_welcome_to_nav_intro)
            }, 1500)
        }
    }
}