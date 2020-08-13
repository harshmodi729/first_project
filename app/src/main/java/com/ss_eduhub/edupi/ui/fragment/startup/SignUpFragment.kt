package com.ss_eduhub.edupi.ui.fragment.startup

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ss_eduhub.edupi.R
import com.ss_eduhub.edupi.base.BaseFragment
import com.ss_eduhub.edupi.base.BaseResult
import com.ss_eduhub.edupi.extension.makeToast
import com.ss_eduhub.edupi.model.SignUpItem
import com.ss_eduhub.edupi.viewmodel.SignUpViewModel
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.lay_toolbar.*

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {
    private lateinit var signUpItem: SignUpItem

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvToolbarTitle.text = getString(R.string.sign_up)
        tvTermCondition.movementMethod = LinkMovementMethod.getInstance()

        val signUpViewModel = ViewModelProviders.of(this)[SignUpViewModel::class.java]
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
                    mContext.makeToast(it.errorMessage)
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