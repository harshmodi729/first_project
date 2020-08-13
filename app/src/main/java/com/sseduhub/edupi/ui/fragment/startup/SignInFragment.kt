package com.sseduhub.edupi.ui.fragment.startup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.sseduhub.edupi.R
import com.sseduhub.edupi.base.BaseFragment
import com.sseduhub.edupi.base.BaseResult
import com.sseduhub.edupi.common.Constants
import com.sseduhub.edupi.extension.hideKeyboard
import com.sseduhub.edupi.extension.makeToast
import com.sseduhub.edupi.ui.activity.MainActivity
import com.sseduhub.edupi.viewmodel.SignInViewModel
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
                    edMobile.requestFocus()
                    edPassword.setText("")
                    Constants.userProfileData = it.item
                    startActivity(Intent(mContext, MainActivity::class.java))
                    mContext.finish()
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
            showProgressDialog(laySigIn, ivDialogBg)
            signInViewModel.userSignIn(
                mContext,
                edMobile.text.toString().trim(),
                edPassword.text.toString().trim()
            )
        }
    }
}