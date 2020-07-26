package com.hmatter.first_project.ui.fragment.startup

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvToolbarTitle.text = getString(R.string.sign_up)
        chTerms.movementMethod = LinkMovementMethod.getInstance()
        val backButtonCallback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        activity?.onBackPressedDispatcher!!.addCallback(viewLifecycleOwner, backButtonCallback)
        btnToolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btnSignUp.setOnClickListener {
            it.findNavController().navigate(R.id.action_nav_sign_up_to_nav_otp)
        }
    }
}