package com.sseduhub.edupi.ui.fragment.startup

import android.os.Bundle
import android.os.Handler
import androidx.navigation.fragment.findNavController
import com.sseduhub.edupi.R
import com.sseduhub.edupi.base.BaseFragment

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