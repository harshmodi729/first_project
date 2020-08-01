package com.hmatter.first_project.ui.fragment.startup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseFragment
import com.hmatter.first_project.base.BaseResult
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

        btnToolbarBack.visibility = View.GONE
        tvToolbarTitle.text = getString(R.string.sign_in)

        signInViewModel = ViewModelProviders.of(this)[SignInViewModel::class.java]
        signInViewModel.signInLiveData.observe(viewLifecycleOwner, Observer {
            hideProgressDialog(ivDialogBg)
            when (it) {
                is BaseResult.Success -> {
                    edMobile.setText("")
                    edPassword.setText("")
                    startActivity(Intent(mContext, MainActivity::class.java))
                }
                is BaseResult.Error -> {
                    mContext.makeToast(it.errorMessage)
                }
            }
        })

        tvSignUp.setOnClickListener {
            it.findNavController().navigate(R.id.action_nav_sign_in_to_nav_sign_up)
        }
        tvForgotPassword.setOnClickListener {
            it.findNavController().navigate(R.id.action_nav_sign_in_to_forgotPasswordFragment)
        }
        btnSignIn.setOnClickListener {
            mContext.hideKeyboard(edMobile)
            if (edMobile.text!!.isNotEmpty() && edPassword.text!!.isNotEmpty()) {
                showProgressDialog(laySigIn, ivDialogBg)
                signInViewModel.userSignIn(
                    mContext,
                    edMobile.text.toString().trim(),
                    edPassword.text.toString().trim()
                )
            } else {
                mContext.makeToast("Please enter valid username and password.")
            }
        }
    }
}