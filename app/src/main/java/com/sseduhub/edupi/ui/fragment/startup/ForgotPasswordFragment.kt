package com.sseduhub.edupi.ui.fragment.startup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.sseduhub.edupi.R
import com.sseduhub.edupi.base.BaseFragment
import com.sseduhub.edupi.base.BaseResult
import com.sseduhub.edupi.extension.isBlankOrEmpty
import com.sseduhub.edupi.extension.makeToast
import com.sseduhub.edupi.model.ForgotPasswordItem
import com.sseduhub.edupi.viewmodel.ForgotPasswordViewModel
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import kotlinx.android.synthetic.main.lay_reset_forgot_password.*
import kotlinx.android.synthetic.main.lay_toolbar.*
import kotlinx.android.synthetic.main.lay_username_forgot_password.*

class ForgotPasswordFragment : BaseFragment(R.layout.fragment_forgot_password), TextWatcher {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvToolbarTitle.text = getString(R.string.forgot_password)
        val backButtonCallback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().popBackStack()
                }
            }
        activity?.onBackPressedDispatcher!!.addCallback(viewLifecycleOwner, backButtonCallback)

        val forgotPasswordViewModel =
            ViewModelProviders.of(this)[ForgotPasswordViewModel::class.java]
        forgotPasswordViewModel.verifyMobileLiveData.observe(viewLifecycleOwner, Observer {
            hideProgressDialog(ivDialogBg)
            when (it) {
                is BaseResult.Success -> {
                    mContext.makeToast(it.item)
                    layPhoneNumber.visibility = View.GONE
                    layReset.visibility = View.VISIBLE
                }
                is BaseResult.Error -> {
                    mContext.makeToast(it.errorMessage)
                }
            }
        })
        forgotPasswordViewModel.resetPasswordLiveData.observe(viewLifecycleOwner, Observer {
            hideProgressDialog(ivDialogBg)
            when (it) {
                is BaseResult.Success -> {
                    mContext.makeToast(it.item)
                    findNavController().navigate(R.id.action_nav_forgot_password_to_nav_sign_in)
                }
                is BaseResult.Error -> {
                    mContext.makeToast(it.errorMessage)
                }
            }
        })

        btnToolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
        edPhone.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(
                textview: TextView?,
                actioId: Int,
                keyEvent: KeyEvent?
            ): Boolean {
                if (!edPhone.text.toString().isBlankOrEmpty()) {
                    if (actioId == EditorInfo.IME_ACTION_DONE) {
                        forgotPasswordViewModel.verifyMobileNumber(edPhone.text.toString()).apply {
                            showProgressDialog(layForgotPassword, ivDialogBg)
                        }
                        return true
                    }
                } else mContext.makeToast("Please enter valid phone number.")
                return false
            }
        })
        edOldPassword.addTextChangedListener(this)
        edNewPassword.addTextChangedListener(this)
        btnResetPassword.setOnClickListener {
            val forgotPasswordItem = ForgotPasswordItem()
            forgotPasswordItem.oldPassword = edOldPassword.text.toString().trim()
            forgotPasswordItem.newPassword = edNewPassword.text.toString().trim()
            showProgressDialog(layForgotPassword, ivDialogBg)
            forgotPasswordViewModel.resetPassword(mContext, forgotPasswordItem, true)
        }
    }

    override fun afterTextChanged(p0: Editable?) {

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(value: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (value.isNullOrEmpty()) {
            btnResetPassword.isEnabled = false
        } else {
            if (!btnResetPassword.isEnabled)
                btnResetPassword.isEnabled = true
        }
    }
}