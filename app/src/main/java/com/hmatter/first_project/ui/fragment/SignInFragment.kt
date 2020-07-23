package com.hmatter.first_project.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseFragment
import com.hmatter.first_project.extension.hideKeyboard
import com.hmatter.first_project.extension.makeToast
import com.hmatter.first_project.ui.activity.MainActivity
import com.hmatter.first_project.viewmodel.SignInViewModel
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {
    private lateinit var signInViewModel: SignInViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        signInViewModel = ViewModelProviders.of(this)[SignInViewModel::class.java]
        btnToolbarBack.visibility = View.GONE
        tvToolbarTitle.text = getString(R.string.sign_in)
        tvSignUp.setOnClickListener {
            it.findNavController().navigate(R.id.action_nav_sign_in_to_nav_sign_up)
        }
        tvForgotPassword.setOnClickListener {
            it.findNavController().navigate(R.id.action_nav_sign_in_to_forgotPasswordActivity)
        }
        btnSignIn.setOnClickListener {
            mContext.hideKeyboard(edUserName)
            if (edUserName.text!!.isNotEmpty() && edPassword.text!!.isNotEmpty()) {
                laySigIn.post {
                    showProgressDialog(laySigIn, ivDialogBg)
                }
                Handler().postDelayed({
                    hideProgressDialog(ivDialogBg)
                    startActivity(Intent(mContext, MainActivity::class.java))
                }, 3000)
            } else {
                mContext.makeToast("Please enter valid username and password.")
            }
        }
    }
}