package com.ss_eduhub.ui.fragment.startup

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ss_eduhub.R
import com.ss_eduhub.base.BaseFragment
import com.ss_eduhub.base.BaseResult
import com.ss_eduhub.extension.makeToast
import com.ss_eduhub.extension.makeToastForServerError
import com.ss_eduhub.model.SignUpItem
import com.ss_eduhub.viewmodel.SignUpViewModel
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {
    private lateinit var signUpItem: SignUpItem

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvToolbarTitle.text = getString(R.string.sign_up)
        tvTermCondition.movementMethod = LinkMovementMethod.getInstance()

        val signUpViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        signUpViewModel.registrationLiveData.observe(viewLifecycleOwner, Observer {
            hideProgressDialog(ivDialogBg)
            when (it) {
                is BaseResult.Success -> {
                    mContext.makeToast(it.item)
                    val action =
                        SignUpFragmentDirections.actionNavSignUpToNavOtp(signUpItem.phoneNumber)
                    findNavController().navigate(action)
                }
                is BaseResult.Error -> {
                    mContext.makeToastForServerError(it)
                }
            }
        })

        val backButtonCallback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_nav_sign_up_to_nav_sign_in)
                }
            }
        activity?.onBackPressedDispatcher!!.addCallback(viewLifecycleOwner, backButtonCallback)
        btnToolbarBack.setOnClickListener {
            it.findNavController().navigate(R.id.action_nav_sign_up_to_nav_sign_in)
        }

        btnSignUp.setOnClickListener {
            signUpItem = SignUpItem()
            signUpItem.userName = edUserName.text.toString().trim()
            signUpItem.email = edEmail.text.toString().trim()
            signUpItem.phoneNumber = edPhone.text.toString().trim()
            signUpItem.password = edPassword.text.toString().trim()
            signUpItem.confirmPassword = edConfirmPassword.text.toString().trim()
            signUpItem.isTermsConditionChecked = chTerms.isChecked
            showProgressDialog(laySignUp, ivDialogBg)
            signUpViewModel.userRegistration(signUpItem)
        }
    }
}