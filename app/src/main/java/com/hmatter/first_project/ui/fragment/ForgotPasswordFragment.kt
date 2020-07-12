package com.hmatter.first_project.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.hmatter.first_project.R
import com.hmatter.first_project.base.BaseFragment
import com.hmatter.first_project.extension.makeToast
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
        btnToolbarBack.setOnClickListener {
            findNavController().popBackStack()
        }
        edUserName.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(
                textview: TextView?,
                actioId: Int,
                keyEvent: KeyEvent?
            ): Boolean {
                if (edUserName.text!!.isNotEmpty() && edUserName.text!!.isNotBlank()) {
                    if (actioId == EditorInfo.IME_ACTION_DONE) {
                        layUserName.visibility = View.GONE
                        layReset.visibility = View.VISIBLE
                        return true
                    }
                } else mContext.makeToast("Please enter username")
                return false
            }
        })
        edPassword.addTextChangedListener(this)
        edConfirmPassword.addTextChangedListener(this)
        btnResetPassword.setOnClickListener {
            it.findNavController().navigate(R.id.action_nav_forgot_password_to_nav_sign_in)
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